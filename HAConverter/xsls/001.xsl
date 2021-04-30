<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
	version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xs="http://www.w3.org/2001/XMLSchema"
	xmlns:son="ye.son@astkorea.net"
	xmlns:idPkg="http://ns.adobe.com/AdobeInDesign/idml/1.0/packaging"
	exclude-result-prefixes="xs son idPkg">
	
	<xsl:output encoding="UTF-8" indent="no" version="1.0" omit-xml-declaration="yes" cdata-section-elements="Contents"/>

	<xsl:param name="filename" />

	<xsl:variable name="last_directory" select="replace(son:last($filename, '\\'), '.idml', '')" />
	<!--<xsl:variable name="filepath" select="collection(concat(son:getpath(base-uri(.), '/'), '/../', $last_directory ,'/Stories/?select=*.xml;recurse=yes'))" />-->
	<xsl:variable name="filepath" select="collection(concat('file:////', replace(replace($filename, '\idml', ''), '\\', '/'), '/Stories/?select=*.xml;recurse=yes'))" />

	<xsl:template match="@* | node()">
		<xsl:copy>
			<xsl:apply-templates select="@*, node()" />
		</xsl:copy>
	</xsl:template>
	
	<xsl:template match="/">
		<xsl:for-each select="$filepath/node()">
			<xsl:variable name="str0" select="son:last(base-uri(.), '/')" />
			<xsl:variable name="store_path" select="replace(replace($filename, '\.idml', ''), '\\', '/')" />
			<xsl:variable name="store_path1" select="concat('file:////', replace(replace($filename, '\.idml', ''), '\\', '/'))" />
			
			<xsl:result-document href="{$store_path1}/Stories/{$str0}">
				<xsl:text disable-output-escaping="yes">&lt;?xml version="1.0" encoding="UTF-8" standalone="yes"?></xsl:text>
				<xsl:text>&#xa;</xsl:text>
				<xsl:apply-templates select="." mode="abc" />
			</xsl:result-document>
		</xsl:for-each>
		<dummy />
	</xsl:template>

	<xsl:template match="@* | node()" mode="abc">
		<xsl:copy>
			<xsl:apply-templates select="@* | node()" mode="abc" />
		</xsl:copy>
	</xsl:template>

	<xsl:template match="text()" mode="abc" priority="10">
		<xsl:choose>
			<xsl:when test="ancestor::CharacterStyleRange/@AppliedCharacterStyle[matches(., 'C_OSD')]">
				<xsl:analyze-string select="." regex="(.*)(\.)(\s)(:)$">
					<xsl:matching-substring>
						<xsl:value-of select="regex-group(1)" />
						<xsl:value-of select="regex-group(2)" />
						<xsl:value-of select="'&#xFEFF;&#x20;'" />
						<xsl:value-of select="regex-group(4)" />
                    </xsl:matching-substring>
					<xsl:non-matching-substring>
						<xsl:analyze-string select="." regex="(.*)(\.)(\s)(\()">
							<xsl:matching-substring>
								<xsl:value-of select="regex-group(1)" />
								<xsl:value-of select="regex-group(2)" />
								<xsl:value-of select="'&#xFEFF;&#x20;'" />
								<xsl:value-of select="regex-group(4)" />
							</xsl:matching-substring>
							<xsl:non-matching-substring>
								<xsl:analyze-string select="." regex="(.*)(\.)(\s)(.*)">
									<xsl:matching-substring>
										<xsl:value-of select="regex-group(1)" />
										<xsl:value-of select="regex-group(2)" />
										<xsl:value-of select="'&#xFEFF;&#x20;'" />
										<xsl:value-of select="regex-group(4)" />
									</xsl:matching-substring>
							
									<xsl:non-matching-substring>
										<xsl:value-of select="."/>
                                    </xsl:non-matching-substring>
                                </xsl:analyze-string>
                            </xsl:non-matching-substring>
                        </xsl:analyze-string>
                	</xsl:non-matching-substring>
                </xsl:analyze-string>
            </xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="." />
            </xsl:otherwise>
    	</xsl:choose>
	</xsl:template>


	<xsl:function name="son:getpath">
		<xsl:param name="str"/>
		<xsl:param name="char"/>
		<xsl:value-of select="string-join(tokenize($str, $char)[position() ne last()], $char)" />
	</xsl:function>

	<xsl:function name="son:last">
		<xsl:param name="str"/>
		<xsl:param name="char"/>
		<xsl:value-of select="tokenize($str, $char)[last()]" />
	</xsl:function>
</xsl:stylesheet>