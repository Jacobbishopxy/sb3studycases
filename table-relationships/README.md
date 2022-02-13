# One-to-One/One-to-Many/Many-to-Many Relationships

## One-to-one

- Data Model

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

- Repository

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

- Data Model

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

- Repository

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

## Many-to-many

多对多关系通过 _@ManyToMany_ 注解进行定义，同样的可以添加 _@RestResource_

- Data Model

  ```java
  @Entity
  public class Author {

      @Id
      @GeneratedValue
      private long id;

      @Column(nullable = false)
      private String name;

      @ManyToMany(cascade = CascadeType.ALL)
      @JoinTable(name = "book_author",
        joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "author_id",
        referencedColumnName = "id"))
      private List<Book> books;

      //standard constructors, getters, setters
  }
  ```

  为 _Book_ 类添加关联：

  ```java
  public class Book {

    //...

    @ManyToMany(mappedBy = "books")
    private List<Author> authors;

    //...
  }
  ```

- Repository

  ```java
  public interface AuthorRepository extends CrudRepository<Author, Long> { }
  ```

- 关联资源

  与之前一样，首先创建资源：

  ```bash
  curl -i -X POST -H "Content-Type:application/json" -d "{\"name\":\"author1\"}" http://localhost:8080/authors
  ```

  接着添加第二个 _Book_ 至数据库：

  ```bash
  curl -i -X POST -H "Content-Type:application/json" -d "{\"title\":\"Book 2\"}" http://localhost:8080/books
  ```

  在 _Author_ 执行 GET 请求，查看关联的 URL：

  ```json
  {
  	"name": "author1",
  	"_links": {
  		"self": {
  			"href": "http://localhost:8080/authors/1"
  		},
  		"author": {
  			"href": "http://localhost:8080/authors/1"
  		},
  		"books": {
  			"href": "http://localhost:8080/authors/1/books"
  		}
  	}
  }
  ```

  现在可以通过 endpoint _authors/1/books_ 的 PUT 方法为两个 _Book_ 与 _Author_ **创建关联**了。

  为了发送若干 URIs 我们需要用一个换行符来分隔他们：

  ```bash
  curl -i -X PUT -H "Content-Type:text/uri-list" --data-binary @uris.txt http://localhost:8080/authors/1/books
  ```

  _uris.txt_ 文件包含了 books 的 URIs：

  ```txt
  http://localhost:8080/books/1
  http://localhost:8080/books/2
  ```

  通过 GET 请求**验证 books 已经被关联到 author 上**：

  ```bash
  curl -i -X GET http://localhost:8080/authors/1/books
  ```

  我们将得到 JSON 响应：

  ```json
  {
  	"_embedded": {
  		"books": [
  			{
  				"title": "Book 1",
  				"_links": {
  					"self": {
  						"href": "http://localhost:8080/books/1"
  					}
  					//...
  				}
  			},
  			{
  				"title": "Book 2",
  				"_links": {
  					"self": {
  						"href": "http://localhost:8080/books/2"
  					}
  					//...
  				}
  			}
  		]
  	},
  	"_links": {
  		"self": {
  			"href": "http://localhost:8080/authors/1/books"
  		}
  	}
  }
  ```

  同样的可以通过 DELETE 方法**移除关联**：

  ```bash
  curl -i -X DELETE http://localhost:8080/authors/1/books/1
  ```

## Projection

在只需要关心一个实体的部分属性时，可以使用 Projection 映射。

```java
@Projection(
  name = "customBook",
  types = { Book.class })
public interface CustomBook {
    String getTitle();
}
```

这里对一个接口使用 _@Projection_ 注解。通过 _name_ 属性来自定义映射的名称，以及 _types_ 属性定义需要映射的对象。

通常而言，我们可以通过 _http://localhost:8080/books/1?projection={projection name}_ 来访问映射结果。

注意需要定义 _Projection_ 于 models 的相同 package 中。另一种方案是使用 _RepositoryRestConfigurerAdapter_ 来显式声明：

```java
@Configuration
public class RestConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(
      RepositoryRestConfiguration repositoryRestConfiguration, CorsRegistry cors) {
        repositoryRestConfiguration.getProjectionConfiguration()
          .addProjection(CustomBook.class);
    }
}
```

## Adding new data to projections

现在让我们学习如何为 projection 添加新数据。前面讨论过可以通过 Projection 来选择那些属性需要显示。除此之外我们还可以添加原本没有的属性。

### 隐藏数据

默认情况下，id 是不会在 view 中显示，因此我们可以显式的包含 _id_：

```java
@Projection(
  name = "customBook",
  types = { Book.class })
public interface CustomBook {
    // 显示隐藏属性
    @Value("#{target.id}")
    long getId();

    String getTitle();
}
```

注意也可以通过 _@JsonIgnore_ 来展示隐藏属性。

### 计算数据

同样的可以添加计算属性：

```java
@Projection(name = "customBook", types = { Book.class })
public interface CustomBook {

    @Value("#{target.id}")
    long getId();

    String getTitle();

    // 添加计算属性
    @Value("#{target.getAuthors().size()}")
    int getAuthorCount();
}
```

### 更为便利的访问关联资源

最后如果我们需要经常访问相关联的资源 - 例子中为 book 的 authors，可以通过显示的包含资源来避免额外的请求：

```java
@Projection(
  name = "customBook",
  types = { Book.class })
public interface CustomBook {

    @Value("#{target.id}")
    long getId();

    String getTitle();

    // 关联资源
    List<Author> getAuthors();

    @Value("#{target.getAuthors().size()}")
    int getAuthorCount();
}
```

## Excerpts

Excerpts 是资源集合的默认 projections。

```java
@RepositoryRestResource(excerptProjection = CustomBook.class)
public interface BookRepository extends CrudRepository<Book, Long> {}
```

正如之前所提到的，excerpts 仅会自动的应用于资源集合。如果是单个资源，则需要使用 _projection_ 参数。因为如果单个资源使用的是 Projections 作为默认视图，这将使我们很难知道如何从局部视图更新资源。最后重要的一点是 projections 和 excerpts 都是只读的。

## Sqlite Dialect

### 扩展 Dialect

首先需要做的是扩展 _org.hibernate.dialect.Dialect_ 类，用于注册 SQLite 所提供的数据类型。

```java
public class SQLiteDialect extends Dialect {

    public SQLiteDialect() {
        registerColumnType(Types.BIT, "integer");
        registerColumnType(Types.TINYINT, "tinyint");
        registerColumnType(Types.SMALLINT, "smallint");
        registerColumnType(Types.INTEGER, "integer");
        // other data types
    }
}
```

接着我们需要重写一些默认的 _Dialect_ 行为。

### Identity 列支持

本例中，通过自定义实现 _IdentityColumnSupport_ 告诉 Hibernate 如何让 SQLite 处理 _@Id_ 列：

```java
public class SQLiteIdentityColumnSupport extends IdentityColumnSupportImpl {

    @Override
    public boolean supportsIdentityColumns() {
        return true;
    }

    @Override
    public String getIdentitySelectString(String table, String column, int type)
      throws MappingException {
        return "select last_insert_rowid()";
    }

    @Override
    public String getIdentityColumnString(int type) throws MappingException {
        return "integer";
    }
}
```

### 禁用 Constraints Handling

SQLite 没有提供数据库约束，因此我们需要重载 primary 以及 foreign keys 的方法：

```java
@Override
public boolean hasAlterTable() {
    return false;
}

@Override
public boolean dropConstraints() {
    return false;
}

@Override
public String getDropForeignKeyString() {
    return "";
}

@Override
public String getAddForeignKeyConstraintString(String cn,
  String[] fk, String t, String[] pk, boolean rpk) {
    return "";
}

@Override
public String getAddPrimaryKeyConstraintString(String constraintName) {
    return "";
}
```

## DataSource 配置

同样的因为**Spring Boot 没有提供 SQLite 数据库的配置支持**，因此需要暴露自定义的 _DataSource_ bean：

```java
@Autowired Environment env;

@Bean
public DataSource dataSource() {
    final DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(env.getProperty("driverClassName"));
    dataSource.setUrl(env.getProperty("url"));
    dataSource.setUsername(env.getProperty("user"));
    dataSource.setPassword(env.getProperty("password"));
    return dataSource;
}
```

最后是在 _persistence.properties_ 文件中配置：

```properties
driverClassName=org.sqlite.JDBC
url=jdbc:sqlite:memory:myDb?cache=shared
username=sa
password=sa
hibernate.dialect=com.baeldung.dialect.SQLiteDialect
hibernate.hbm2ddl.auto=create-drop
hibernate.show_sql=true
```

注意这里的 cache 是 _shared_ 的，这使得数据库更新对多个数据库连接可见。

**综上所述，使用上述配置后，app 将会启动一个名为 myDb 的内存数据库**，剩余的 Spring Data Rest 配置可以占用。
