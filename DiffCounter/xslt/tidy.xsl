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

    <!--<xsl:output method="xml" encoding="UTF-8" indent="no"/>-->
    <xsl:output method="xml" encoding="UTF-8" indent="yes" use-character-maps="map"/>
    <xsl:strip-space elements="Roots group each"/>

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

	<xsl:template match="group | each">
		<xsl:text>&#xA;&#x9;</xsl:text>
		<xsl:copy>
			<xsl:apply-templates select="@* | node()"/>
			<xsl:text>&#xA;&#x9;</xsl:text>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="seg">
		<xsl:variable name="this" select="."/>
		<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
		<xsl:copy>
			<xsl:apply-templates select="@*"/>
			<xsl:analyze-string select="." regex="(&lt;Description-Cell&gt;.*?&lt;/Description-Cell&gt;)">
				<xsl:matching-substring>
					<xsl:value-of select="regex-group(1)"/>
				</xsl:matching-substring>
				<xsl:non-matching-substring>
					<xsl:analyze-string select="." regex="(&lt;Description-NoHTML&gt;.*?&lt;/Description-NoHTML&gt;)">
						<xsl:matching-substring>
							<xsl:value-of select="regex-group(1)"/>
						</xsl:matching-substring>
						<xsl:non-matching-substring>
							<xsl:analyze-string select="." regex="(&lt;Description&gt;.*?&lt;/Description&gt;)">
								<xsl:matching-substring>
									<xsl:value-of select="regex-group(1)"/>
								</xsl:matching-substring>
								<xsl:non-matching-substring>
									<xsl:analyze-string select="." regex="(&lt;UnorderList_\d-Cell&gt;.*&lt;/UnorderList_\d-Cell&gt;)">
										<xsl:matching-substring>
											<xsl:value-of select="regex-group(1)"/>
										</xsl:matching-substring>
										<xsl:non-matching-substring>
											<xsl:analyze-string select="." regex="(&lt;C_Important-Semi.*&lt;/C_Important-Semi&gt;)">
												<xsl:matching-substring>
													<xsl:value-of select="regex-group(1)"/>
												</xsl:matching-substring>
												<xsl:non-matching-substring>
													<xsl:analyze-string select="." regex="(&lt;C_Important.*&lt;/C_Important&gt;)">
														<xsl:matching-substring>
															<xsl:value-of select="regex-group(1)"/>
														</xsl:matching-substring>
														<xsl:non-matching-substring>
															<xsl:analyze-string select="." regex="(&lt;C_CrossReference.*?&lt;/C_CrossReference&gt;)">
																<xsl:matching-substring>
																	<xsl:value-of select="regex-group(1)"/>
																</xsl:matching-substring>
																<xsl:non-matching-substring>
																	<xsl:analyze-string select="." regex="(&lt;C_SingleStep.*&lt;/C_SingleStep&gt;)">
																		<xsl:matching-substring>
																			<xsl:value-of select="regex-group(1)"/>
																		</xsl:matching-substring>
																		<xsl:non-matching-substring>
																			<xsl:analyze-string select="." regex="(&lt;Img-Left&gt;.*&lt;/Img-Left&gt;)">
																				<xsl:matching-substring>
																					<xsl:value-of select="regex-group(1)"/>
																				</xsl:matching-substring>
																				<xsl:non-matching-substring>
																					<xsl:analyze-string select="." regex="(&lt;C_FontChange.*?/C_FontChange&gt;)">
																						<xsl:matching-substring>
																							<xsl:value-of select="regex-group(1)"/>
																						</xsl:matching-substring>
																						<xsl:non-matching-substring>
																							<xsl:analyze-string select="." regex="(&lt;CharTag.*?/CharTag&gt;)">
																								<xsl:matching-substring>
																									<xsl:value-of select="regex-group(1)"/>
																								</xsl:matching-substring>
																								<xsl:non-matching-substring>
																									<xsl:analyze-string select="." regex="(&lt;C_Image.*&lt;/C_Image&gt;)">
																										<xsl:matching-substring>
																											<xsl:value-of select="regex-group(1)"/>
																										</xsl:matching-substring>
																										<xsl:non-matching-substring>
																											<xsl:analyze-string select="." regex="(&lt;MMI.*?/MMI&gt;)">
																												<xsl:matching-substring>
																													<xsl:value-of select="regex-group(1)"/>
																												</xsl:matching-substring>
																												<xsl:non-matching-substring>
																													<xsl:analyze-string select="." regex="(&lt;C_NoBreak.*&lt;/C_NoBreak&gt;)">
																														<xsl:matching-substring>
																															<xsl:value-of select="regex-group(1)"/>
																														</xsl:matching-substring>
																														<xsl:non-matching-substring>
																															<xsl:analyze-string select="." regex="(&lt;C_URL.*?/C_URL&gt;)">
																																<xsl:matching-substring>
																																	<xsl:value-of select="regex-group(1)"/>
																																</xsl:matching-substring>
																																<xsl:non-matching-substring>
																																	<xsl:analyze-string select="." regex="(&lt;xref.*?/&gt;)">
																																		<xsl:matching-substring>
																																			<xsl:value-of select="regex-group(1)"/>
																																		</xsl:matching-substring>
																																		<xsl:non-matching-substring>
																																			<xsl:analyze-string select="." regex="(&lt;text.*?&lt;/text&gt;)">
																																				<xsl:matching-substring>
																																					<xsl:value-of select="regex-group(1)"/>
																																				</xsl:matching-substring>
																																				<xsl:non-matching-substring>
																																					<xsl:analyze-string select="." regex="(&lt;Img-Center.*&lt;/Img-Center&gt;)">
																																						<xsl:matching-substring>
																																							<xsl:value-of select="regex-group(1)"/>
																																						</xsl:matching-substring>
																																						<xsl:non-matching-substring>
																																							<xsl:analyze-string select="." regex="(&lt;C_CrossReference.*?&lt;/C_CrossReference&gt;)">
																																								<xsl:matching-substring>
																																									<xsl:value-of select="regex-group(1)"/>
																																								</xsl:matching-substring>
																																								<xsl:non-matching-substring>
																																									<xsl:analyze-string select="." regex="(&lt;([^/]*?)&gt;)([^&lt;&gt;]+)$">
																																										<xsl:matching-substring>
																																											<xsl:value-of select="regex-group(1)"/>
																																											<xsl:value-of select="regex-group(3)"/>
																																											<xsl:value-of select="if ( contains(regex-group(2), '&#x20;') ) 
																																																  then concat('&lt;/', substring-before(regex-group(2), '&#x20;'), '&gt;') 
																																																  else concat('&lt;/', regex-group(2), '&gt;') "/>
																																										</xsl:matching-substring>
																																										<xsl:non-matching-substring>
																																											<xsl:analyze-string select="." regex="^(.*)&lt;/(.*?)&gt;">
																																												<xsl:matching-substring>
																																													<!-- Here I am -->
																																													<xsl:choose>
																																														<xsl:when test="$this/preceding-sibling::seg[1][contains(., 'status=')]">
																																															<xsl:variable name="content" select="$this/preceding-sibling::seg[1]"/>
																																															<xsl:variable name="status" select="substring-before(substring-after($content, 'status=&quot;'), '&quot;')"/>
																																															<xsl:value-of select="concat('&lt;', regex-group(2), ' status=&quot;', $status, '&quot;', '&gt;')"/>
																																														</xsl:when>
																																														<xsl:otherwise>
																																															<xsl:value-of select="concat('&lt;', regex-group(2), '&gt;')"/>
																																														</xsl:otherwise>
																																													</xsl:choose>
																																													<!--<xsl:value-of select="concat('&lt;', regex-group(2), '&gt;')"/>-->
																																													<xsl:value-of select="regex-group(1)"/>
																																													<xsl:value-of select="concat('&lt;/', regex-group(2), '&gt;')"/>
																																												</xsl:matching-substring>
																																												<xsl:non-matching-substring>
																																													<xsl:value-of select="."/>
																																												</xsl:non-matching-substring>
																																											</xsl:analyze-string>
																																										</xsl:non-matching-substring>
																																									</xsl:analyze-string>
																																								</xsl:non-matching-substring>
																																							</xsl:analyze-string>
																																						</xsl:non-matching-substring>
																																					</xsl:analyze-string>
																																				</xsl:non-matching-substring>
																																			</xsl:analyze-string>
																																		</xsl:non-matching-substring>
																																	</xsl:analyze-string>
																																</xsl:non-matching-substring>
																															</xsl:analyze-string>
																														</xsl:non-matching-substring>
																													</xsl:analyze-string>
																												</xsl:non-matching-substring>
																											</xsl:analyze-string>
																										</xsl:non-matching-substring>
																									</xsl:analyze-string>
																								</xsl:non-matching-substring>
																							</xsl:analyze-string>
																						</xsl:non-matching-substring>
																					</xsl:analyze-string>
																				</xsl:non-matching-substring>
																			</xsl:analyze-string>
																		</xsl:non-matching-substring>
																	</xsl:analyze-string>
																</xsl:non-matching-substring>
															</xsl:analyze-string>
														</xsl:non-matching-substring>
													</xsl:analyze-string>
												</xsl:non-matching-substring>
											</xsl:analyze-string>
										</xsl:non-matching-substring>
									</xsl:analyze-string>
								</xsl:non-matching-substring>
							</xsl:analyze-string>
						</xsl:non-matching-substring>
					</xsl:analyze-string>
				</xsl:non-matching-substring>
			</xsl:analyze-string>
		</xsl:copy>
	</xsl:template>

</xsl:stylesheet>