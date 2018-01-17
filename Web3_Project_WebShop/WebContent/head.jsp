
<%
	String colour = "yellow";
	Cookie[] cookies = request.getCookies();
	if (cookies.length != 0) {
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("colour")) {
				colour = cookie.getValue();
			}
		}
	}
%>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta charset="UTF-8">
<meta hhtp-equiv="Refresh" content="0"> 
<title>${param.title}</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<%
	if (colour.equals("yellow")) {
%>
<link rel="stylesheet" type="text/css" href="css/yellow.css">
<%
	} else {
%>
<link rel="stylesheet" type="text/css" href="css/red.css">
<%
	}
%>
</head>
