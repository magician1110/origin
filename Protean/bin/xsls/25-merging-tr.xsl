<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:ast="http://www.astkorea.net/"
    exclude-result-prefixes="xs ast"
    version="2.0">

	<xsl:param name="curdir"/>
	<xsl:param name="basic"/>
	<xsl:variable name="path" select="concat('file:///', replace($curdir, '\\', '/'), '/_translate/', '?select=TR_*.xml')"/>
	<xsl:variable name="files" select="collection($path)"/>

   	<xsl:output method="xml" encoding="UTF-8" omit-xml-declaration="yes"/>
	<xsl:strip-space elements="*"/>
	<xsl:preserve-space elements="item"/>

	<xsl:template match="/">
		<xsl:variable name="filename" select="concat('file:///', replace($curdir, '\\', '/'), '/_translate/25-merged-tr.xml')"/>
		<dummy/>
		<xsl:result-document method="xml" href="{$filename}">
			<xsl:for-each select="$files">
				<xsl:if test="ends-with(substring-before(document-uri(.), '.xml'), $basic)">
					<xsl:apply-templates/>
				</xsl:if>
			</xsl:for-each>
		</xsl:result-document>
	</xsl:template>

	<xsl:template match="@* | node()">
		<xsl:copy>
    		<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="next">
		<xsl:text>~~</xsl:text>
	</xsl:template>

	<xsl:template match="paragraph">
		<xsl:variable name="id" select="@id"/>
		<listitem ID="{@id}">
			<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
			<item LV_name="{$basic}">
    			<xsl:apply-templates />
    		</item>
			<xsl:for-each select="$files[not(ends-with(substring-before(document-uri(.), '.xml'), $basic))]">
				<xsl:variable name="LV_name" select="tokenize(substring-before(document-uri(.), '.xml'), '_')[last()]"/>
				<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
				<item LV_name="{$LV_name}">
    				<xsl:apply-templates select="/root/paragraph[@id=$id]/node()"/>
    			</item>
    		</xsl:for-each>
    		<xsl:text>&#xA;&#x9;</xsl:text>
		</listitem>
	</xsl:template>

	<xsl:template match="ph[@outputclass='no-trans']">
		<xsl:variable name="LV_name" select="ast:getFile(substring-before(document-uri(root()), '.xml'), '_')"/>
		<ph>
			<xsl:apply-templates select="@*"/>
			<xsl:choose>
				<!-- decimal point -->
				<xsl:when test="matches(., '^[+-]?[0-9]+[.][0-9]+$')">
					<xsl:choose>
						<xsl:when test="$LV_name = ('ALB', 'BUL', 'CRO', 'CZE', 'DAN', 'DUT', 'EST', 'FIN', 'FRA', 'DEU', 'GRE', 'HUN', 'IND', 'ITA', 'KAZ', 'LAT', 'LTU', 'MKD', 'SPA', 'NOR', 'POL', 'POR', 'ROM', 'RUS', 'SER', 'SLK', 'SLV', 'SWE', 'TUR', 'UKR', 'UZB', 'MON')">
							<xsl:analyze-string select="." regex="([+-]?[0-9]+)[.]([0-9]+)">
								<xsl:matching-substring>
									<xsl:value-of select="concat(regex-group(1), ',', regex-group(2))"/>
								</xsl:matching-substring>
								<xsl:non-matching-substring>
									<xsl:value-of select="."/>
								</xsl:non-matching-substring>
							</xsl:analyze-string>
						</xsl:when>
						<xsl:otherwise>
							<xsl:apply-templates />
						</xsl:otherwise>
					</xsl:choose>
				</xsl:when>
				<!-- thousands separator -->
				<xsl:when test="matches(., '^[0-9]{1,3}(,[0-9]{3})*$')">
					<xsl:choose>
						<!-- period separator -->
						<xsl:when test="$LV_name = ('POR', 'DAN', 'DUT', 'GRE', 'ITA', 'MKD', 'POR-US', 'ROM', 'SPA-US', 'SPA', 'TUR', 'VIE')">
							<xsl:choose>
								<xsl:when test="$LV_name = 'POR'">
									<xsl:value-of select="if ( string-length(.) &gt;= 6 ) then replace(., ',', '.') else replace(., ',', '')"/>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="replace(., ',', '.')"/>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:when>
						<!-- space separator -->
						<xsl:when test="$LV_name = ('BUL', 'CRO', 'CZE', 'FIN', 'FRA', 'FRA-US', 'HUN', 'LAT', 'LTU', 'NOR', 'POL', 'RUS', 'SER', 'SLK', 'SLV', 'SWE', 'UKR', 'UZB')">
							<xsl:value-of select="replace(., ',', '&#x20;')"/>
						</xsl:when>
						<!-- delete comma -->
						<xsl:when test="$LV_name = ('ALB', 'MMR', 'EST', 'MON')">
							<xsl:value-of select="replace(., ',', '')"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:apply-templates />
						</xsl:otherwise>
					</xsl:choose>
				</xsl:when>
				<xsl:otherwise>
					<xsl:apply-templates />
				</xsl:otherwise>
			</xsl:choose>
		</ph>
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