<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:ast="http://www.astkorea.net/"
    exclude-result-prefixes="xs ast"
    version="2.0">

	<xsl:character-map name="cm">
	    <xsl:output-character character="&lt;" string="&lt;"/>
	    <xsl:output-character character="&gt;" string="&gt;"/>
	    <xsl:output-character character="&amp;" string="&amp;"/>
	</xsl:character-map>

   	<xsl:output method="xml" encoding="UTF-8" standalone="yes" indent="no" use-character-maps="cm"/>
    <xsl:preserve-space elements="*"/>

    <xsl:template match="@* | node()">
    	<xsl:copy>
    		<xsl:apply-templates select="@* | node()" />
    	</xsl:copy>
    </xsl:template>

    <xsl:template match="root">
    	<xsl:text>&#xA;</xsl:text>
		<xsl:copy>
			<xsl:analyze-string select="." regex="(&#xA;)">
				<xsl:matching-substring>
				</xsl:matching-substring>
				<xsl:non-matching-substring>
					<xsl:text>&#xA;&#x9;</xsl:text>
					<listitem>
						<xsl:analyze-string select="." regex="(&#x9;)">
							<xsl:matching-substring>
							</xsl:matching-substring>
							<xsl:non-matching-substring>
								<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
								<item>
									<xsl:value-of select="."/>
								</item>
							</xsl:non-matching-substring>
						</xsl:analyze-string>
						<xsl:text>&#xA;&#x9;</xsl:text>
					</listitem>
				</xsl:non-matching-substring>
			</xsl:analyze-string>
			<xsl:text>&#xA;</xsl:text>
		</xsl:copy>
    </xsl:template>

</xsl:stylesheet>