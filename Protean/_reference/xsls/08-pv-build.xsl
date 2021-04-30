<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:ast="http://www.astkorea.net/"
    exclude-result-prefixes="xs ast"
    version="2.0">

	<xsl:variable name="ref-raw" select="document(concat(ast:getPath(base-uri(), '/'), '/ref-raw.xml'))" as="document-node()"/>
	<xsl:variable name="ref-tmp" select="document(concat(ast:getPath(base-uri(), '/'), '/ref-tmp.xml'))" as="document-node()"/>
	<xsl:variable name="LV_name" select="$ref-raw/root/listitem[1]/item[1]/@LV_name"/>

   	<xsl:output method="xml" encoding="UTF-8" indent="no"/>
    <xsl:strip-space elements="*"/>
    <xsl:preserve-space elements="item"/>

	<xsl:template match="/">
		<xsl:variable name="root">
			<xsl:apply-templates />
		</xsl:variable>
		<xsl:copy-of select="$root"/>
		<xsl:variable name="filename" select="concat(ast:getPath(base-uri(), '/'), '/../_pvdb/Revised_Base_', $LV_name, '.xml')"/>
		<xsl:result-document method="xml" href="{$filename}">
			<xsl:text>&#xA;</xsl:text>
			<root>
				<xsl:attribute name="LV_name" select="$LV_name"/>
				<xsl:for-each select="$root//listitem[@rev]">
					<xsl:variable name="rev" select="@rev"/>
					<xsl:text>&#xA;&#x9;</xsl:text>
					<paragraph>
						<xsl:attribute name="id" select="@ID"/>
						<xsl:attribute name="rev" select="$rev"/>
						<xsl:apply-templates select="$ref-raw/root/listitem[@ID=$rev]/item/node()"/>
					</paragraph>
				</xsl:for-each>
				<xsl:text>&#xA;</xsl:text>
			</root>
		</xsl:result-document>
	</xsl:template>

	<xsl:template match="root">
		<xsl:text>&#xA;</xsl:text>
		<xsl:copy>
			<xsl:apply-templates select="@* | node()" />
			<xsl:text>&#xA;</xsl:text>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="listitem">
		<xsl:variable name="listitem" select="."/>
		<xsl:variable name="item" select="item[1]"/>
		<xsl:variable name="tmpItems" select="$ref-tmp/root/listitem/item"/>
		<xsl:variable name="i" select="1" as="xs:integer"/>
		<xsl:variable name="limit" select="count($ref-tmp/root/listitem/item)" as="xs:integer"/>

		<xsl:variable name="Exist">
			<xsl:call-template name="yes_or_no">
				<xsl:with-param name="item" select="$item"/>
				<xsl:with-param name="i" select="$i" />
				<xsl:with-param name="tmpItems" select="$tmpItems" />
				<xsl:with-param name="limit" select="$limit" />
			</xsl:call-template>
		</xsl:variable>

		<xsl:choose>
			<xsl:when test="tokenize($Exist, '\|')[1] = 'no'">
				<xsl:text>&#xA;&#x9;</xsl:text>
				<xsl:copy>
					<xsl:apply-templates select="@* | node()" />
					<xsl:text>&#xA;&#x9;</xsl:text>
				</xsl:copy>
			</xsl:when>
			<xsl:otherwise>
				<xsl:variable name="ID" select="tokenize($Exist, '\|')[2]"/>
				<xsl:variable name="first" select="$ref-raw/root/listitem[@ID=$ID]/item"/>
				<xsl:text>&#xA;&#x9;</xsl:text>
				<xsl:copy>
					<xsl:attribute name="ID" select="@ID"/>
					<xsl:attribute name="rev" select="$ID"/>
					<xsl:for-each select="$listitem/item">
						<xsl:choose>
							<xsl:when test="position() = 1">
								<xsl:apply-templates select="$first" mode="meta1"/>
							</xsl:when>
							<xsl:otherwise>
								<xsl:apply-templates select="." mode="meta2">
									<xsl:with-param name="first" select="$first"/>
								</xsl:apply-templates>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:for-each>
					<xsl:text>&#xA;&#x9;</xsl:text>
				</xsl:copy>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

    <xsl:template match="@* | node()">
    	<xsl:copy>
    		<xsl:apply-templates select="@* | node()"/>
    	</xsl:copy>
    </xsl:template>

    <xsl:template match="@* | node()" mode="meta1">
    	<xsl:copy>
    		<xsl:apply-templates select="@* | node()" mode="meta1"/>
    	</xsl:copy>
    </xsl:template>

	<xsl:template match="text()" mode="#all" priority="10">
		<xsl:value-of select="replace(replace(., '\(\s+', '('), '\s+\)', ')')"/>
	</xsl:template>

    <xsl:template match="@* | node()" mode="meta2">
    	<xsl:param name="first"/>
    	<xsl:copy>
    		<xsl:apply-templates select="@* | node()" mode="meta2">
    			<xsl:with-param name="first" select="$first"/>
    		</xsl:apply-templates>
    	</xsl:copy>
    </xsl:template>

	<xsl:template match="item">
		<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
		<xsl:copy>
			<xsl:apply-templates select="@* | node()" />
		</xsl:copy>
	</xsl:template>

	<xsl:template match="item" mode="meta1">
		<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
		<xsl:copy>
			<xsl:apply-templates select="@* | node()" />
		</xsl:copy>
	</xsl:template>

	<xsl:template match="item" mode="meta2">
		<xsl:param name="first"/>
		<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
		<xsl:copy>
			<xsl:apply-templates select="@*" />
			<xsl:apply-templates select="node()" mode="meta2">
				<xsl:with-param name="first" select="$first"/>
			</xsl:apply-templates>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="apiname" mode="meta2">
		<xsl:param name="first"/>
		<xsl:variable name="rev" select="@rev"/>
		<xsl:copy>
			<xsl:apply-templates select="$first/apiname/@*" />
			<xsl:apply-templates mode="meta2"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="xref" mode="meta2">
		<xsl:param name="first"/>
		<xsl:copy>
			<xsl:apply-templates select="$first/xref/@href" />
		</xsl:copy>
	</xsl:template>

	<xsl:template name="yes_or_no">
		<xsl:param name="item"/>
		<xsl:param name="i"/>
		<xsl:param name="tmpItems"/>
		<xsl:param name="limit"/>
		<xsl:choose>
			<xsl:when test="$i &lt;= $limit and deep-equal($item, $tmpItems[$i])">
				<xsl:value-of select="concat('yes', '|', $tmpItems[$i]/parent::listitem/@ID)" />
			</xsl:when>
			<xsl:when test="$i &gt; $limit">
				<xsl:value-of select="'no'" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="yes_or_no">
					<xsl:with-param name="item" select="$item"/>
					<xsl:with-param name="i" select="$i + 1" />
					<xsl:with-param name="tmpItems" select="$tmpItems" />
					<xsl:with-param name="limit" select="$limit" />
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
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