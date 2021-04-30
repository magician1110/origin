<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:functx="http://www.functx.com"
    exclude-result-prefixes="xs functx"
    version="2.0">

    <xsl:output method="xml" encoding="UTF-8" indent="no" />
    <xsl:strip-space elements="Roots"/>

	<xsl:template match="@* | node()">
		<xsl:copy>
			<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="@diff">
	</xsl:template>

	<xsl:template match="*[starts-with(name(), 'MMI')]/@hash">
	</xsl:template>

	<xsl:template match="Roots">
		<xsl:text>&#xA;</xsl:text>
		<Roots>
			<xsl:apply-templates select="@* | node()"/>
			<xsl:text>&#xA;</xsl:text>
		</Roots>
	</xsl:template>

	<xsl:template match="*[parent::Roots]">
		<xsl:choose>
			<xsl:when test="name() = 'group'">
				<xsl:variable name="founds" as="xs:boolean*">
					<xsl:call-template name="compare">
						<xsl:with-param name="this" select="."/>
						<xsl:with-param name="groups" select="parent::Roots/group except ."/>
						<xsl:with-param name="index" select="1"/>
						<xsl:with-param name="limit" select="count(parent::Roots/group) - 1"/>
					</xsl:call-template>
				</xsl:variable>

				<xsl:choose>
					<xsl:when test="@diff = 'A' and true() = $founds">
					</xsl:when>
					<xsl:when test="@diff = 'A' and false() = $founds">
						<xsl:text>&#xA;&#x9;</xsl:text>
						<xsl:copy>
							<xsl:attribute name="status">Deleted</xsl:attribute>
							<xsl:apply-templates select="@* | node()"/>
						</xsl:copy>
					</xsl:when>
					<xsl:when test="@diff = 'B' and true() = $founds">
						<xsl:text>&#xA;&#x9;</xsl:text>
						<xsl:copy>
							<xsl:attribute name="status">Moved</xsl:attribute>
							<xsl:apply-templates select="@* | node()"/>
						</xsl:copy>
					</xsl:when>
					<xsl:when test="@diff = 'B' and false() = $founds">
						<xsl:text>&#xA;&#x9;</xsl:text>
						<xsl:copy>
							<xsl:attribute name="status">Added</xsl:attribute>
							<xsl:apply-templates select="@* | node()"/>
						</xsl:copy>
					</xsl:when>
				</xsl:choose>
			</xsl:when>
			<xsl:when test="name() = 'Empty'">
				<xsl:for-each select="Table/Cell">
					<xsl:if test="string()">
						<xsl:text>&#xA;&#x9;</xsl:text>
						<xsl:apply-templates select="node()"/>
					</xsl:if>
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
				<xsl:text>&#xA;&#x9;</xsl:text>
				<xsl:copy>
					<xsl:apply-templates select="@* | node()"/>
				</xsl:copy>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="compare">
		<xsl:param name="this" as="element()"/>
		<xsl:param name="groups" as="element()*"/>
		<xsl:param name="index" as="xs:integer"/>
		<xsl:param name="limit" as="xs:integer"/>

		<xsl:choose>
			<xsl:when test="functx:sequence-deep-equal($this/*, $groups[$index]/*)">
				<xsl:value-of select="true()"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="false()"/>
				<xsl:if test="$index &lt;= $limit">
					<xsl:call-template name="compare">
						<xsl:with-param name="this" select="$this"/>
						<xsl:with-param name="groups" select="$groups"/>
						<xsl:with-param name="index" select="$index + 1"/>
						<xsl:with-param name="limit" select="$limit"/>
					</xsl:call-template>
				</xsl:if>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:function name="functx:sequence-deep-equal" as="xs:boolean">
		<xsl:param name="seq1" as="item()*"/>
		<xsl:param name="seq2" as="item()*"/>
		<xsl:sequence select="every $i in 1 to max((count($seq1),count($seq2))) satisfies deep-equal($seq1[$i],$seq2[$i])"/>
	</xsl:function>

</xsl:stylesheet>