package servlets;

import model.CalcularHuellaCarbono;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

	@WebServlet("/huella")
	public class Controlador extends HttpServlet {
	    private static final long serialVersionUID = 1L;
	    private CalcularHuellaCarbono service = new CalcularHuellaCarbono();

	    @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {

	        request.setCharacterEncoding("UTF-8");

	        String transporte = request.getParameter("transporte");
	        String kmStr = request.getParameter("km");
	        String diasStr = request.getParameter("dias");
	        String operacion = request.getParameter("operacion");

	        // Validaciones
	        if (transporte == null || kmStr == null || diasStr == null || operacion == null ||
	                transporte.isBlank() || kmStr.isBlank() || diasStr.isBlank() || operacion.isBlank()) {
	            response.sendRedirect(request.getContextPath() + "/huella?error=Faltan+datos");
	            return;
	        }

	        double km;
	        int dias;
	        try {
	            km = Double.parseDouble(kmStr);
	            dias = Integer.parseInt(diasStr);
	            if (km <= 0 || dias < 1 || dias > 7)
	                throw new IllegalArgumentException();
	        } catch (Exception e) {
	            response.sendRedirect(request.getContextPath() + "/huella?error=Datos+no+válidos");
	            return;
	        }

	        double kgSem = service.calcularSemanal(transporte, km, dias);

	        switch (operacion) {
	            case "CALC_SEMANAL":
	                response.sendRedirect(request.getContextPath() +
	                        String.format("/huella?op=%s&kg=%.2f", operacion, kgSem));
	                break;

	            case "CLASIFICAR_IMPACTO":
	                String impacto = service.clasificarImpacto(kgSem);
	                response.sendRedirect(request.getContextPath() +
	                        String.format("/huella?op=%s&kg=%.2f&impacto=%s", operacion, kgSem, impacto));
	                break;

	            case "PROPONER_COMPENSACION":
	                String comp = service.proponerCompensacion(kgSem);
	                response.sendRedirect(request.getContextPath() +
	                        String.format("/huella?op=%s&kg=%.2f&comp=%s",
	                                operacion, kgSem, java.net.URLEncoder.encode(comp, "UTF-8")));
	                break;

	            default:
	                response.sendRedirect(request.getContextPath() + "/huella?error=Operación+inválida");
	        }
	    }

	    @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {

	        request.setCharacterEncoding("UTF-8");

	        // Copiamos parámetros a atributos
	        if (request.getParameter("error") != null)
	            request.setAttribute("error", request.getParameter("error"));

	        if (request.getParameter("op") != null)
	            request.setAttribute("op", request.getParameter("op"));

	        if (request.getParameter("kg") != null)
	            request.setAttribute("kg", request.getParameter("kg"));

	        if (request.getParameter("impacto") != null)
	            request.setAttribute("impacto", request.getParameter("impacto"));

	        if (request.getParameter("comp") != null)
	            request.setAttribute("comp", request.getParameter("comp"));

	        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/huella.jsp");
	        rd.forward(request, response);
	    }
}
	