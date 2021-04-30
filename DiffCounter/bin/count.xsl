<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    version="2.0">

    <xsl:output method="xml" encoding="UTF-8" indent="no"/>
    <xsl:strip-space elements="*"/>
    <xsl:preserve-space elements="seg"/>

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

	<xsl:template match="seg">
		<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
		<xsl:copy>
			<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="group">
		<xsl:text>&#xA;&#x9;</xsl:text>
		<xsl:copy>
			<xsl:apply-templates select="@*"/>
			<xsl:choose>
				<xsl:when test="@status = 'Deleted'">
					<xsl:attribute name="del">1</xsl:attribute>
				</xsl:when>
				<xsl:when test="@status = 'Moved'">
					<xsl:attribute name="mov">1</xsl:attribute>
				</xsl:when>
				<xsl:when test="@status = 'Added'">
					<xsl:attribute name="add">
						<xsl:value-of select="count(seg)"/>
					</xsl:attribute>
				</xsl:when>
			</xsl:choose>
			<xsl:apply-templates select="node()"/>
			<xsl:text>&#xA;&#x9;</xsl:text>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="each">
		<xsl:text>&#xA;&#x9;</xsl:text>
		<xsl:copy>
			<xsl:apply-templates select="@*"/>
			<xsl:choose>
				<xsl:when test="@status = 'Deleted'">
					<xsl:attribute name="status">Deleted</xsl:attribute>
					<xsl:attribute name="del">1</xsl:attribute>
					<xsl:apply-templates select="node()"/>
				</xsl:when>
				<xsl:when test="@status = 'Moved'">
					<xsl:attribute name="status">Moved</xsl:attribute>
					<xsl:attribute name="mov">1</xsl:attribute>
					<xsl:apply-templates select="node()"/>
				</xsl:when>
				<xsl:when test="@status = 'Added'">
					<xsl:attribute name="status">Added</xsl:attribute>
					<xsl:attribute name="add">
						<xsl:value-of select="count(seg)"/>
					</xsl:attribute>
					<xsl:apply-templates select="node()"/>
				</xsl:when>
				<xsl:when test="@status = 'Modified'">
					<xsl:attribute name="status">Modified</xsl:attribute>
					<xsl:attribute name="mod">
						<xsl:value-of select="count(seg)"/>
					</xsl:attribute>
					<xsl:apply-templates select="node()"/>
				</xsl:when>
				<xsl:when test="not(@status)">
					<xsl:for-each select="seg">
						<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
						<seg>
							<xsl:choose>
								<xsl:when test="not(*[@status='Deleted']) and *[@status!='Deleted']">
									<xsl:if test="*[@status='Added']">
										<xsl:attribute name="add">1</xsl:attribute>
									</xsl:if>
									<xsl:if test="*[@status='Moved']">
										<xsl:attribute name="mov">1</xsl:attribute>
									</xsl:if>
									<xsl:if test="*[@status='Modified']">
										<xsl:attribute name="mod">1</xsl:attribute>
									</xsl:if>
								</xsl:when>
								<xsl:when test="*[@status='Deleted'] and *[@status!='Deleted']">
									<xsl:if test="*[@status='Added']">
										<xsl:attribute name="add">1</xsl:attribute>
									</xsl:if>
									<xsl:if test="*[@status='Moved']">
										<xsl:attribute name="mov">1</xsl:attribute>
									</xsl:if>
									<xsl:if test="*[@status='Modified']">
										<xsl:attribute name="mod">1</xsl:attribute>
									</xsl:if>
								</xsl:when>
								<xsl:when test="*[@status='Deleted'] and not(*[@status!='Deleted'])">
									<xsl:choose>
										<xsl:when test="text[@status='Deleted']/following-sibling::node()[1][name()!='text'] and
														text[@status='Deleted']/following-sibling::node()[2][name()='text'][@status='Deleted']">
											<xsl:attribute name="mod">1</xsl:attribute>
										</xsl:when>
										<xsl:when test="*[@status='Deleted']">
											<xsl:attribute name="del">1</xsl:attribute>
										</xsl:when>
									</xsl:choose>
								</xsl:when>
							</xsl:choose>
							<xsl:apply-templates select="node()"/>
						</seg>
					</xsl:for-each>
				</xsl:when>
			</xsl:choose>
			<xsl:text>&#xA;&#x9;</xsl:text>
		</xsl:copy>
	</xsl:template>

</xsl:stylesheet>