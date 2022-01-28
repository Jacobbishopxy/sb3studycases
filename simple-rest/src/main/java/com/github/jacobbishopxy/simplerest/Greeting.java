/**
 * 资源类
 *
 * 使用 Jackson JSON 序列化，默认包含于 web starter 包中
 */

package com.github.jacobbishopxy.simplerest;

public class Greeting {

  private final long id;
  private final String content;

  public Greeting(long id, String content) {
    this.id = id;
    this.content = content;
  }

  public long getId() {
    return id;
  }

  public String getContent() {
    return content;
  }
}
