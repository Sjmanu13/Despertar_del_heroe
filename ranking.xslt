<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:template match="/">

    <html>
      <head>
        <link rel="stylesheet" href="styles.css"/>
      </head>
      <body>
        <h1>Ranking de Héroes</h1>
        <table border="1">
          <tr>
            <th>Posición</th>
            <th>Héroe</th>
            <th>Puntos</th>
          </tr>
          <xsl:for-each select="ranking/rank[name != '']">
            <tr>
              <td>
                <xsl:value-of select="position()"/>
              </td>
              <td>
                <xsl:value-of select="name"/>
              </td>
              <td>
                <xsl:value-of select="points"/>
              </td>
            </tr>
          </xsl:for-each>
        </table>
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>