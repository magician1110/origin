<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:ast="http://www.astkorea.net/"
    exclude-result-prefixes="xs ast"
    version="2.0">

	<xsl:param name="curdir"/>
	<xsl:param name="basic"/>
  	<xsl:variable name="root" select="/root"/>
  	<xsl:variable name="header" select="document('header.xml')" as="document-node()"/>
  	<xsl:variable name="lang_code_tables" select="document(concat(ast:getPath(base-uri(), '/'), '/../../_Reference/language_codes/lang_code_tables.xml'))" as="document-node()"/>
  	<xsl:variable name="LV_name_source" select="$basic"/>
  	<xsl:variable name="LV_name_target" select="/root/listitem[1]/item[@LV_name!=$basic]/@LV_name"/>

   	<xsl:output method="xml" encoding="UTF-8"/>
	<xsl:strip-space elements="*"/>
	<xsl:preserve-space elements="seg item"/>

	<xsl:template match="@* | node()">
		<xsl:copy>
    		<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="/">
		<dummy/>
		<xsl:for-each select="$LV_name_target">
			<xsl:variable name="filename" select="concat('file:///', replace($curdir, '\\', '/'), '/out/', $LV_name_source, '_', ., '.tmx')"/>
			<xsl:result-document method="xml" href="{$filename}">
				<xsl:apply-templates select="$header/tmx">
					<xsl:with-param name="source" select="$LV_name_source"/>
					<xsl:with-param name="target" select="."/>
				</xsl:apply-templates>
			</xsl:result-document>
		</xsl:for-each>
	</xsl:template>

	<xsl:template match="tmx">
		<xsl:param name="source"/>
		<xsl:param name="target"/>
		<xsl:text>&#xA;</xsl:text>
		<xsl:copy>
			<xsl:apply-templates select="@*"/>
    		<xsl:apply-templates select="header">
				<xsl:with-param name="source" select="$source"/>
				<xsl:with-param name="target" select="$target"/>
    		</xsl:apply-templates>
    		<xsl:text>&#xA;</xsl:text>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="header">
		<xsl:param name="source"/>
		<xsl:param name="target"/>
		<xsl:variable name="source_country" select="$lang_code_tables/root/language[@LV_name=$source]/@country"/>
		<xsl:variable name="target_country" select="$lang_code_tables/root/language[@LV_name=$target]/@country"/>

		<xsl:text>&#xA;&#x9;</xsl:text>
		<xsl:copy>
    		<xsl:apply-templates select="@* except (@adminlang, @srclang)"/>
    		<xsl:attribute name="adminlang" select="$lang_code_tables/root/language[@LV_name=$source]/@lang_code"/>
    		<xsl:attribute name="srclang" select="$lang_code_tables/root/language[@LV_name=$source]/@lang_code"/>
    		<xsl:apply-templates select="node()"/>
    		<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
			<prop type="x-Recognizers">RecognizeAll</prop>
			<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
			<prop type="x-TMName">
				<xsl:value-of select="concat('Protean-TV-', $source_country, '-', $target_country)"/>
			</prop>
			<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
			<prop type="x-TokenizerFlags">DefaultFlags</prop>
    		<xsl:text>&#xA;&#x9;</xsl:text>
		</xsl:copy>
		<xsl:call-template name="body">
			<xsl:with-param name="source" select="$source"/>
			<xsl:with-param name="target" select="$target"/>
		</xsl:call-template>
	</xsl:template>

	<xsl:template name="body">
		<xsl:param name="source"/>
		<xsl:param name="target"/>

  		<xsl:variable name="source_lang_code" select="$lang_code_tables/root/language[@LV_name=$source]/@lang_code"/>
   		<xsl:variable name="target_lang_code" select="$lang_code_tables/root/language[@LV_name=$target]/@lang_code"/>

		<xsl:text>&#xA;&#x9;</xsl:text>
		<body>
			<xsl:for-each select="$root/listitem">
				<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
				<tu>
					<xsl:attribute name="creationdate" select="format-dateTime(current-dateTime(),'[Y0001][M01][D01]T[h01][m01][s01]Z')"/>
					<xsl:attribute name="creationid" select="'FACC'"/>
					<xsl:attribute name="changedate" select="format-dateTime(current-dateTime(),'[Y0001][M01][D01]T[h01][m01][s01]Z')"/>
					<xsl:attribute name="changeid" select="'FACC'"/>

					<xsl:text>&#xA;&#x9;&#x9;&#x9;</xsl:text>
					<tuv xml:lang="{$source_lang_code}">
						<seg>
							<xsl:apply-templates select="item[@LV_name=$source]"/>
						</seg>
					</tuv>
					<xsl:text>&#xA;&#x9;&#x9;&#x9;</xsl:text>
					<tuv xml:lang="{$target_lang_code}">
						<seg>
							<xsl:apply-templates select="item[@LV_name=$target]"/>
						</seg>
					</tuv>
					<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
				</tu>
			</xsl:for-each>
    		<xsl:text>&#xA;&#x9;</xsl:text>
		</body>
	</xsl:template>

	<xsl:template match="item">
		<xsl:for-each select="node()">
			<xsl:choose>
				<xsl:when test="self::*[matches(name(), '^(image|xref|next)$')]">
					<xsl:variable name="i" select="count(preceding-sibling::*) + 1"/>
					<ph>
						<xsl:attribute name="i" select="$i"/>
						<xsl:attribute name="type" select="1"/>
					</ph>
				</xsl:when>
				<xsl:when test="self::*[name()='ph'][@outputclass='C_Font-index']">
					<xsl:variable name="i" select="count(preceding-sibling::*) + 1"/>
					<bpt>
						<xsl:attribute name="i" select="$i"/>
						<xsl:attribute name="type" select="1"/>
						<xsl:attribute name="x" select="@x"/>
					</bpt>
					<xsl:apply-templates/>
					<ept>
						<xsl:attribute name="i" select="$i"/>
					</ept>
				</xsl:when>
				<xsl:when test="self::*[not(matches(name(), '^(image|xref)$'))]">
					<xsl:variable name="i" select="count(preceding-sibling::*) + 1"/>
					<bpt>
						<xsl:attribute name="i" select="$i"/>
						<xsl:attribute name="type" select="if ( @*[name()!='x'] ) then concat('x-LockedContent', $i) else 1"/>
						<xsl:attribute name="x" select="@x"/>
					</bpt>
					<xsl:for-each select="node()">
						<xsl:choose>
							<xsl:when test="self::*">
								<bpt>
									<xsl:attribute name="i" select="$i + 1"/>
									<xsl:attribute name="type" select="if ( @*[name()!='x'] ) then concat('x-LockedContent', $i + 1) else 1"/>
									<xsl:attribute name="x" select="parent::*/@x + 1"/>
								</bpt>
								<xsl:value-of select="."/>
								<ept>
									<xsl:attribute name="i" select="$i + 1"/>
								</ept>
							</xsl:when>
							<xsl:when test="self::text()">
								<xsl:value-of select="."/>
							</xsl:when>
						</xsl:choose>
					</xsl:for-each>
					<ept>
						<xsl:attribute name="i" select="$i"/>
					</ept>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="."/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:for-each>
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