<html>
<head><title></title>
<body>
<h1 style="text-align: right;">&nbsp;<img class="irc_mut" style="margin-top: 0px; display: block; margin-left: auto; margin-right: auto;" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSveHeEADJPsNQfNPuT9xQ9kQcJg8O_dYD9qHSxfibpqw-htdZh" alt="Imagen relacionada" width="155" height="116" /></h1>
<p>Estimado Asesor de Bienestar: ${cardName}</p>
<p>Le informamos que su pedido con orden de compra:<strong> ${orden}</strong> ha salido ya de nuestro almacen y se encuentra
<br>en ruta para que lo reciba en pr&oacute;ximo d&iacute;as.</p>
<p>El env&iacute;o contiene:</p>
<table border="1" cellspacing="0" >
<tbody>
<tr>
        <th>Item</th>
        <th>Descripcion</th>
        <th>Cantidad enviada</th>
</tr>
<#list references as reference>
<tr>
            <td>${reference.itemCode}</td>
            <td>${reference.description}</td>
            <#assign x=reference.quantity>
            <#if x?number == 0>
                <td>Pendiente de surtir</td>
            <#else>
                <td align="center">${x?number}</td>
            </#if>
</tr>
</#list>
</tbody>
</table>
<p><br />La guia de estafeta para que monitoree el envio es: <br>
    ${resourceLink}${guiaEstafeta}
</p>
<p>Para cualquier duda o comentario, le invitamos a comunicarse a los numeros de servicio al cliente marcando al <br>
<strong>(55) 5864-9070</strong> o al correo <a href="mailto:aclaracionenvios.mex@nikkenlat.com">aclaracionenvios.mex@nikkenlat.com</a></p>
<p>Atentamente,</p>
<p><strong>NIKKEN LATINOAMERICA</strong><br /><strong>A SU SERVICIO</strong></p>
<p><strong>
<a title="facebook" href="${social.urlFacebook}"><img src="https://68ef2f69c7787d4078ac-7864ae55ba174c40683f10ab811d9167.ssl.cf1.rackcdn.com/facebook-icon_128x128.png" width="26" height="26"/> </a>
<a title="twitter" href="${social.urlTwitter}"><img src="https://68ef2f69c7787d4078ac-7864ae55ba174c40683f10ab811d9167.ssl.cf1.rackcdn.com/twitter-icon_128x128.png" width="26" height="26"/> </a>
<a title="youtube" href="${social.urlYoutube}"><img src="https://68ef2f69c7787d4078ac-7864ae55ba174c40683f10ab811d9167.ssl.cf1.rackcdn.com/youtube-icon_128x128.png" width="26" height="26"/> </a>
<a title="instagram" href="${social.urlInstagram}"><img src="https://68ef2f69c7787d4078ac-7864ae55ba174c40683f10ab811d9167.ssl.cf1.rackcdn.com/instagram-icon_128x128.png" width="26" height="26"/></a><br/></strong></p>
</body>
</html>