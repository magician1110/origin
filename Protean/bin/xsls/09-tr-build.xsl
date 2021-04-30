<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:ast="http://www.astkorea.net/"
    exclude-result-prefixes="xs ast"
    version="2.0">

	<xsl:variable name="LV_name" select="document(concat(ast:getPath(base-uri(), '/'), '/ref-raw.xml'))/root/listitem[1]/item[1]/@LV_name"/>
	<xsl:variable name="Revised" select="document(concat(ast:getPath(base-uri(), '/'), '/../_pvdb/Revised_Base_', $LV_name, '.xml'))" as="document-node()"/>

  	<xsl:output method="xml" encoding="UTF-8" indent="no"/>
    <xsl:strip-space elements="*"/>
    <xsl:preserve-space elements="item"/>

	<xsl:template match="/">
		<dummy/>
		<xsl:variable name="filename" select="concat(ast:getPath(base-uri(), '/'), '/../_translate/TR_Paragraph_Variable_', $LV_name, '.xml')"/>
		<xsl:result-document method="xml" href="{$filename}">
			<xsl:apply-templates />
		</xsl:result-document>
	</xsl:template>

	<xsl:template match="@* | node()">
		<xsl:copy>
			<xsl:apply-templates select="@* | node()" />
		</xsl:copy>
	</xsl:template>

	<xsl:template match="root">
		<xsl:text>&#xA;</xsl:text>
		<xsl:copy>
			<xsl:attribute name="LV_name" select="$LV_name"/>
			<xsl:apply-templates select="@* | node()" />
			<xsl:text>&#xA;</xsl:text>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="listitem">
		<xsl:variable name="ID" select="@ID"/>
		<xsl:choose>
			<xsl:when test="$Revised/root/paragraph[@rev=$ID]">
			</xsl:when>
			<xsl:otherwise>
				<xsl:text>&#xA;&#x9;</xsl:text>
				<paragraph id="{@ID}">
					<xsl:apply-templates />
				</paragraph>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="item">
		<xsl:apply-templates/>
	</xsl:template>

	<xsl:template match="text()" priority="10">
		<xsl:analyze-string select="." regex="~~">
			<xsl:matching-substring>
				<next/>
			</xsl:matching-substring>
			<xsl:non-matching-substring>
				<xsl:value-of select="replace(replace(., '\(\s+', '('), '\s+\)', ')')"/>
			</xsl:non-matching-substring> 
		</xsl:analyze-string>
	</xsl:template>

	<xsl:function name="ast:getPath">
		<xsl:param name="str"/>
		<xsl:param name="char"/>
		<xsl:value-of select="string-join(tokenize($str, $char)[position() ne last()], '/')" />
	</xsl:function>

	<xsl:function name="ast:getFile">
		<xsl:param name="str"/>
		<xsl:param name="char"/>
		<xsl:value-of select="tokenize($str, $char)[last()]" />
	</xsl:function>

</xsl:stylesheet>