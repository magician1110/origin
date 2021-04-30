<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    version="2.0">

	<xsl:character-map name="map"> 
		<xsl:output-character character="&lt;" string="&lt;"/>
		<xsl:output-character character="&gt;" string="&gt;"/>
	</xsl:character-map>

    <xsl:output method="xml" encoding="UTF-8" indent="no"/>
    <!--<xsl:output method="xml" encoding="UTF-8" indent="no" use-character-maps="map"/>-->
    <xsl:strip-space elements="Roots group"/>

	<xsl:template match="@* | node()">
		<xsl:copy>
			<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="Roots">
		<xsl:text>&#xA;</xsl:text>
		<Roots>
			<xsl:apply-templates select="@* | node()"/>
			<xsl:text>&#xA;</xsl:text>
		</Roots>
	</xsl:template>

	<xsl:template match="*[parent::Roots]" priority="10">
		<xsl:text>&#xA;&#x9;</xsl:text>
		<xsl:choose>
			<xsl:when test="name()='group'">
				<xsl:copy>
					<xsl:apply-templates select="@*"/>
					<xsl:for-each select="*">
						<xsl:choose>
							<xsl:when test="contains(., '¶')">
								<xsl:analyze-string select="." regex="¶">
									<xsl:matching-substring>
									</xsl:matching-substring>
									<xsl:non-matching-substring>
										<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
										<seg>
											<xsl:value-of select="."/>
										</seg>
									</xsl:non-matching-substring>
								</xsl:analyze-string>
							</xsl:when>
							<xsl:otherwise>
								<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
								<seg>
									<xsl:apply-templates select="node()"/>
								</seg>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:for-each>
					<xsl:text>&#xA;&#x9;</xsl:text>
				</xsl:copy>
			</xsl:when>
			<xsl:otherwise>
				<each>
				<xsl:apply-templates select="@*"/>
					<xsl:choose>
						<xsl:when test="contains(., '¶')">
							<xsl:analyze-string select="." regex="¶">
								<xsl:matching-substring>
								</xsl:matching-substring>
								<xsl:non-matching-substring>
									<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
									<seg>
										<xsl:value-of select="."/>
									</seg>
								</xsl:non-matching-substring>
							</xsl:analyze-string>
						</xsl:when>
						<xsl:otherwise>
							<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
							<seg>
								<xsl:apply-templates select="node()"/>
							</seg>
						</xsl:otherwise>
					</xsl:choose>
					<xsl:text>&#xA;&#x9;</xsl:text>
				</each>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

</xsl:stylesheet>