<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:ast="http://www.astkorea.net/"
    exclude-result-prefixes="xs ast"
    version="2.0">

	<xsl:variable name="LV_name" select="document(concat(ast:getPath(base-uri(), '/'), '/ref-raw.xml'))/root/listitem[1]/item[1]/@LV_name"/>
	<xsl:variable name="PV" select="document(concat(ast:getPath(base-uri(), '/'), '/../_pvdb/Paragraph_Variable.xml'))" as="document-node()"/>
	<xsl:variable name="pvItems" select="$PV/root/listitem/item"/>
	<xsl:variable name="IDs" select="/root/listitem/@ID"/>
	<xsl:variable name="limit" select="count($PV/root/listitem/item)"/>

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
			<xsl:apply-templates select="@* | node()" />
			<xsl:text>&#xA;</xsl:text>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="listitem">
		<xsl:variable name="item" select="item"/>
		<xsl:variable name="Exist">
			<xsl:choose>
	        	<xsl:when test="$pvItems[deep-equal(., $item)]">
					<xsl:value-of select="'yes'" />
	        	</xsl:when>
	        	<xsl:otherwise>
					<xsl:value-of select="'no'" />
	        	</xsl:otherwise>
	        </xsl:choose>
		</xsl:variable>

		<xsl:choose>
			<xsl:when test="not(@base) and $Exist = 'no'">
				<xsl:text>&#xA;&#x9;</xsl:text>
				<paragraph id="{ast:getID(.)}">
					<xsl:apply-templates />
				</paragraph>
			</xsl:when>
			<xsl:when test="@base = $IDs and $Exist = 'no'">
				<xsl:text>&#xA;&#x9;</xsl:text>
				<paragraph id="{ast:getID(.)}">
					<xsl:apply-templates />
				</paragraph>
			</xsl:when>
			<xsl:when test="@base and not(@rev) and not(@base = $IDs) and $Exist = 'no'">
				<xsl:text>&#xA;&#x9;</xsl:text>
				<paragraph id="{ast:getID(.)}">
					<xsl:apply-templates />
				</paragraph>
			</xsl:when>
			<xsl:when test="@base and @rev and not(@base = $IDs) and $Exist = 'no'">
				<xsl:text>&#xA;&#x9;</xsl:text>
				<paragraph id="{ast:getID(.)}">
					<xsl:apply-templates />
				</paragraph>
			</xsl:when>
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

	<xsl:function name="ast:getID">
		<xsl:param name="this"/>
		<xsl:variable name="cnt" select="format-number(count($this/preceding-sibling::listitem), '0000')"/>
		<xsl:variable name="ms" select="substring(xs:string(( current-dateTime() - xs:dateTime('1970-01-01T00:00:00') ) div xs:dayTimeDuration('PT0.001S')), 11, 3)"/>
		<xsl:value-of select="concat(format-dateTime(current-dateTime(),'[Y0001][M01][D01]_[h01][m01][s01]_'), $ms, '_', $cnt)"/>
	</xsl:function>

</xsl:stylesheet>