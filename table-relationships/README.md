# One-to-One/One-to-Many/Many-to-Many Relationships

## One-to-one

- Data Model:

  Library 与 Address 的关系为一对一关系。

  _@RestResource_ 注解是可选的，可用于自定义 endpoint。

  **其中值的注意的是，在命名的时候每个资源的名称需要互为不同**。否者我们会遇到一个 _JsonMappingException_ 的信息：“Detected multiple association links with same relation type! Disambiguate association”。

  关联的名称默认为属性名称，也可以使用 _@RestResource_ 注解中的 _rel_ 属性进行自定义：

  ```java
  @OneToOne
  @JoinColumn(name = "secondary_address_id")
  @RestResource(path = "libraryAddress", rel="address")
  private Address secondaryAddress;
  ```

- Repository:

  为了暴露 entities 为资源，需要为它们各自创建 repository 接口，默认继承 _CrudRepository_ 接口。

- 创建资源

  ```bash
  curl -i -X POST -H "Content-Type:application/json" -d '{"name":"My Library"}' http://localhost:8080/libraries
  ```

  API 将会返回 JSON 对象：

  ```json
  {
  	"name": "My Library",
  	"_links": {
  		"self": {
  			"href": "http://localhost:8080/libraries/1"
  		},
  		"library": {
  			"href": "http://localhost:8080/libraries/1"
  		},
  		"address": {
  			"href": "http://localhost:8080/libraries/1/libraryAddress"
  		}
  	}
  }
  ```

  **在创建一个关联之前，发送 GET 请求将会返回空的对象**。如果要添加一个关联，我们需要先创建一个 _Address_ 实例：

  ```bash
  curl -i -X POST -H "Content-Type:application/json" -d '{"location":"Main Street nr 5"}' http://localhost:8080/addresses
  ```

  其返回为：

  ```json
  {
  	"location": "Main Street nr 5",
  	"_links": {
  		"self": {
  			"href": "http://localhost:8080/addresses/1"
  		},
  		"address": {
  			"href": "http://localhost:8080/addresses/1"
  		},
  		"library": {
  			"href": "http://localhost:8080/addresses/1/library"
  		}
  	}
  }
  ```

- 创建关联

  **创建完两个实例之后，接下来就是使用其中一个关联资源来创建关联关系**。

  这个使用 HTTP 的 PUT 方法来完成的，它支持媒体类型 _text/uri-list_ 以及一个用来包含 URI 关联资源的 body。

  因为 _Library_ 实体是关联关系的所有者。所以为一个 library 添加 address：

  ```bash
  curl -i -X PUT -d "http://localhost:8080/addresses/1" -H "Content-Type:text/uri-list" http://localhost:8080/libraries/1/libraryAddress
  ```

  如果成功了返回状态码 204。验证 _library_ 是否关联 _address_：

  ```bash
  curl -i -X GET http://localhost:8080/addresses/1/library
  ```

  这将返回包含着 _My Library_ 名字的 _Library_ JSON 对象。

  同样的，通过调用 DELETE 方法可以 _移除关联_，确保调用的是关联关系所有者下的方法：

  ```bash
  curl -i -X GET http://localhost:8080/addresses/1/library
  ```

## One-to-many

- Data Model:

  ```java
  @Entity
  public class Book {

      @Id
      @GeneratedValue
      private long id;

      @Column(nullable=false)
      private String title;

      @ManyToOne
      @JoinColumn(name="library_id")
      private Library library;

      // standard constructor, getter, setter
  }
  ```

  ```java
  public class Library {

      //...

      @OneToMany(mappedBy = "library")
      private List<Book> books;

      //...

  }
  ```

- Repository:

  ```java
  public class Library {

    //...

    @OneToMany(mappedBy = "library")
    private List<Book> books;

    //...

  }
  ```

- 关联资源

  首先通过 _/books_ 添加 _books_ 实例：

  ```bash
  curl -i -X POST -d "{\"title\":\"Book1\"}" -H "Content-Type:application/json" http://localhost:8080/books
  ```

  返回 JSON：

  ```json
  {
  	"title": "Book1",
  	"_links": {
  		"self": {
  			"href": "http://localhost:8080/books/1"
  		},
  		"book": {
  			"href": "http://localhost:8080/books/1"
  		},
  		"bookLibrary": {
  			"href": "http://localhost:8080/books/1/library"
  		}
  	}
  }
  ```

  返回的结构内我们可以看到关联 endpoint _/books/{bookId}/library_ 被创建了。

  接着通过之前的 PUT 方法 **关联 book 至 library**：

  ```bash
  curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/libraries/1" http://localhost:8080/books/1/library
  ```

  通过 library 的 _/books_ GET 方法 **验证 library 中的 books**：

  ```bash
  curl -i -X GET http://localhost:8080/libraries/1/books
  ```

  返回的 JSON 将会包含 books 的数组：

  ```json
  {
  	"_embedded": {
  		"books": [
  			{
  				"title": "Book1",
  				"_links": {
  					"self": {
  						"href": "http://localhost:8080/books/1"
  					},
  					"book": {
  						"href": "http://localhost:8080/books/1"
  					},
  					"bookLibrary": {
  						"href": "http://localhost:8080/books/1/library"
  					}
  				}
  			}
  		]
  	},
  	"_links": {
  		"self": {
  			"href": "http://localhost:8080/libraries/1/books"
  		}
  	}
  }
  ```

  同样的，通过调用 DELETE 方法可以 _移除关联_：

  ```bash
  curl -i -X DELETE http://localhost:8080/books/1/library
  ```
