<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Calculadora de Huella de Carbono</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; background: #f9f9f9; }
        h1 { color: #2a6f97; }
        form { background: #fff; padding: 20px; border-radius: 10px; width: 350px; box-shadow: 0 0 10px #ccc; }
        label { display: block; margin-top: 10px; }
        input, select { width: 100%; padding: 8px; margin-top: 5px; }
        .msg { margin-top: 20px; font-weight: bold; }
        .error { color: red; }
    </style>
</head>
<body>
<h1>Calculadora de Huella de Carbono</h1>

<form method="post" action="${pageContext.request.contextPath}/huella">
    <label>Transporte:</label>
    <select name="transporte" required>
        <option value="">-- Seleccione --</option>
        <option>COCHE</option>
        <option>AUTOBUS</option>
        <option>TREN</option>
        <option>BICI</option>
        <option>PIE</option>
    </select>

    <label>Kilómetros diarios:</label>
    <input type="number" name="km" step="0.1" min="0" required />

    <label>Días por semana:</label>
    <input type="number" name="dias" min="1" max="7" required />

    <label>Operación:</label>
    <select name="operacion" required>
        <option value="">-- Seleccione --</option>
        <option value="CALC_SEMANAL">Calcular semanal</option>
        <option value="CLASIFICAR_IMPACTO">Clasificar impacto</option>
        <option value="PROPONER_COMPENSACION">Proponer compensación</option>
    </select>

    <button type="submit" style="margin-top:15px;">Calcular</button>
</form>

<div class="msg">
    <c:if test="${not empty error}">
        <p class="error"> ${error}</p>
    </c:if>

    <c:choose>
        <c:when test="${op eq 'CALC_SEMANAL'}">
            Esta semana emitirás <b>${kg} kg CO₂</b>.
        </c:when>
        <c:when test="${op eq 'CLASIFICAR_IMPACTO'}">
            Impacto <b>${impacto}</b> (${kg} kg CO₂).
        </c:when>
        <c:when test="${op eq 'PROPONER_COMPENSACION'}">
            <c:out value="${comp}" />
        </c:when>
    </c:choose>
</div>
</body>
</html>
