# mybatis-generator-ext

```xml
<dependency>
	<groupId>me.aifaq</groupId>
	<artifactId>mybatis-generator-ext</artifactId>
	<version>VERSION</version>
</dependency>
```

# 扩展插件


## MapperPlugin

```xml
<plugin type="me.aifaq.mybatis.generator.plugins.MapperPlugin" />
```

在生成的Mapper类上添加注解 **@org.apache.ibatis.annotations.Mapper**


## ForceSkipMergeSqlMapPlugin

```xml
<plugin type="me.aifaq.mybatis.generator.plugins.ForceSkipMergeSqlMapPlugin" />
```

强制sql mapper.xml 不做merge操作

## SpringDataPagePlugin

```xml
<plugin type="me.aifaq.mybatis.generator.plugins.SpringDataPagePlugin" />
```

使用 **spring-data-commons** 中的分页Bean实现分页查询。

### 原理：

在生成的 ****Example** 类中添加属性：

```java
  protected org.springframework.data.domain.Pageable page;
```

同时在生成的 **sql mapper.xml** 中添加该属性的逻辑判断，如下：

```xml
  <sql id="SpringDataPageSql">
    <if test="page != null">
      <if test="page.sort != null">
        ORDER BY
        <foreach collection="page.sort" item="order" index="index" open="" separator="," close="">
          ${order.property,jdbcType=VARCHAR} ${order.direction,jdbcType=VARCHAR}
        </foreach>
      </if>
      LIMIT #{page.offset,jdbcType=INTEGER},#{page.pageSize,jdbcType=INTEGER}
    </if>
  </sql>
```