package me.aifaq.mybatis.generator.plugins;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;

/**
 * 在生成的Mapper类中添加注解 @Mapper
 *
 * @author Wang Wei
 * @since 20:43 2017/5/15
 */
public class MapperPlugin extends PluginAdapter {
	private FullyQualifiedJavaType mapper;

	public MapperPlugin() {
		super();
		mapper = new FullyQualifiedJavaType("org.apache.ibatis.annotations.Mapper"); //$NON-NLS-1$
	}

	@Override
	public boolean validate(List<String> warnings) {
		// this plugin is always valid
		return true;
	}

	@Override
	public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {

		interfaze.addImportedType(mapper);

		interfaze.addAnnotation("@Mapper");

		return true;
	}
}
