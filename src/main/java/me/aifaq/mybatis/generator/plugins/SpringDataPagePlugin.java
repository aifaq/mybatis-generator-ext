package me.aifaq.mybatis.generator.plugins;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

/**
 * 给selectByExample方法添加分页功能
 *
 * @author Wang Wei
 * @since 20:43 2017/5/15
 */
public class SpringDataPagePlugin extends PluginAdapter {

	private static final String PAGINATION_ID = "SpringDataPageSql";
	private FullyQualifiedJavaType pageable;

	public SpringDataPagePlugin() {
		super();
		pageable = new FullyQualifiedJavaType(
				"org.springframework.data.domain.Pageable"); //$NON-NLS-1$
	}

	@Override
	public boolean validate(List<String> warnings) {
		// this plugin is always valid
		return true;
	}

	void addParameter(Method method) {
		Parameter parameter = new Parameter(pageable, "pageable", "@Param(\"page\")");
		method.addParameter(parameter);
	}

	@Override
	public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {
		final Field field = new Field("page", pageable);
		field.setVisibility(JavaVisibility.PROTECTED);

		final Method setter = new Method("setPage");
		setter.setVisibility(JavaVisibility.PUBLIC);
		setter.addParameter(new Parameter(pageable, "page"));
		setter.addBodyLine("this.page = page;");

		final Method getter = new Method("getPage");
		getter.setVisibility(JavaVisibility.PUBLIC);
		getter.setReturnType(pageable);
		getter.addBodyLine("return page;");

		topLevelClass.addImportedType(pageable);

		topLevelClass.addField(field);
		topLevelClass.addMethod(setter);
		topLevelClass.addMethod(getter);
		return true;
	}

	static final String PAGE;

	static {
		final StringBuilder sb = new StringBuilder();
		sb.append("    <if test=\"page != null\">\n");
		sb.append("      <if test=\"page.sort != null\">\n");
		sb.append("        ORDER BY\n");
		sb.append(
				"        <foreach collection=\"page.sort\" item=\"order\" index=\"index\" open=\"\" separator=\",\" close=\"\">\n");
		sb.append(
				"          ${order.property} ${order.direction}\n");
		sb.append("        </foreach>\n");
		sb.append("      </if>\n");
		sb.append(
				"      LIMIT #{page.offset},#{page.pageSize}\n");
		sb.append("    </if>");
		PAGE = sb.toString();
	}

	@Override
	public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
		XmlElement sqlElement = new XmlElement("sql");
		sqlElement.addAttribute(new Attribute("id", PAGINATION_ID));

		sqlElement.addElement(new TextElement(PAGE));

		document.getRootElement().addElement(sqlElement);
		return true;
	}

	@Override
	public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element,
			IntrospectedTable introspectedTable) {
		addIncludeElement(element);
		return true;
	}

	@Override
	public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement element,
			IntrospectedTable introspectedTable) {
		addIncludeElement(element);
		return true;
	}

	void addIncludeElement(XmlElement element) {
		XmlElement includeElement = new XmlElement("include");
		includeElement.addAttribute(new Attribute("refid", PAGINATION_ID));
		element.addElement(includeElement);
	}
}
