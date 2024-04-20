package org.hits.backend.hackathon_tusur.core.mail;

import org.springframework.stereotype.Component;

@Component
public class MailFormatter {
    public String formatNotificationAboutOpeningWishlist(String username, String link) {
        var builder = new StringBuilder();

        builder.append("""
                <!DOCTYPE html>
                <html lang="en" xmlns="http://www.w3.org/1999/xhtml">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width,initial-scale=1">
                    <meta name="x-apple-disable-message-reformatting">
                    <title></title>
                    <!--[if mso]>
                    <noscript>
                        <xml>
                            <o:OfficeDocumentSettings>
                                <o:PixelsPerInch>96</o:PixelsPerInch>
                            </o:OfficeDocumentSettings>
                        </xml>
                    </noscript>
                    <![endif]-->
                    <style>
                        table, td, div, h1, p {font-family: Arial, sans-serif;}
                    </style>
                </head>
                <body style="margin:0;padding:0;">
                <table role="presentation" style="width:100%;border-collapse:collapse;border:0;border-spacing:0;background:#ffffff;">
                    <tr>
                        <td align="center" style="padding:0;">
                            <table role="presentation" style="width:602px;border-collapse:collapse;border:1px solid #cccccc;border-spacing:0;text-align:left;">
                                <tr>
                                    <td align="center" style="padding:0 0 0 0;background:#70bbd9;">
                                        <img src="https://i.ibb.co/28C84kw/Screenshot-11.png" alt="" style="width:100%; height:auto; display:block;" />
                                    </td>
                                </tr>
                                <tr>
                                    <td style="padding:36px 30px 25px 27px;">
                                        <table role="presentation" style="width:100%;border-collapse:collapse;border:0;border-spacing:0;">
                                            <tr>
                                                <td style="padding:0 0 36px 0;color:#141414;">
                                                    <h1 style="font-size:24px;margin:0 0 20px 0;font-family:Arial,sans-serif;text-align:center">Замечательный праздник!</h1>
                                                    <p style="margin:0 0 12px 0;font-size:16px;line-height:24px;font-family:Arial,sans-serif;">Приближается день рождение\s
                """);
        builder.append(username);
        builder.append("""
                . Поздравьте его с праздником! Подарите ему незабываемые эмоции и впечатления!</p>
                <p style="margin:0;font-size:16px;line-height:24px;font-family:Arial,sans-serif;"><a href=\\"
                """);
        builder.append(link);
        builder.append("""
                \\" style="color:#141414;text-decoration:underline;">Ссылка</a> на сбор средств для подарка</p>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td style="padding:30px;background:#ffdd2d;">
                        <table role="presentation" style="width:100%;border-collapse:collapse;border:0;border-spacing:0;font-size:9px;font-family:Arial,sans-serif;">
                            <tr>
                                <td style="padding:0;width:50%;" align="left">
                                    <p style="margin:0;font-size:14px;line-height:21px;font-family:Arial,sans-serif;color:#141414;">
                                        &reg; Тинькофф Праздник 2024<br/><a href="http://www.example.com" style="color:#141414;text-decoration:underline;">Отписаться</a>
                                    </p>
                                </td>
                                <td style="padding:0;width:50%;" align="right">
                                    <table role="presentation" style="border-collapse:collapse;border:0;border-spacing:0;">
                                        <tr>
                                            <td style="padding:0 0 0 10px;width:38px;">
                                                <a href="https://t.me/tinkofftomsk" style="color:#ffffff;"><img src="https://static-00.iconduck.com/assets.00/telegram-icon-2048x1725-i4kw83ca.png" alt="Twitter"width="38" style="height:auto;display:block;border:0;" /></a>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>
</html>
                """);

        return builder.toString();
    }
}
