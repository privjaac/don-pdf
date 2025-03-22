package com.jaac.pdf;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.borders.DashedBorder;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.jaac.pdf.builder.TableBuilder;
import com.jaac.pdf.main.DonPdf;

import java.util.List;
import java.util.Map;

public class Report {

    record Feature(String name, String description, int rating, String category) {}

    record Testimonial(String company, String quote, String author, String position, String industry) {}

    record Metric(String name, String value, String change, boolean isPositive, String impact) {}

    record Comparison(String aspect, String donPdfAdvantage, String competitorLimitation) {}

    record TechnicalBenefit(String benefit, String explanation, String developmentImpact) {}

    record RoadmapItem(String feature, String description, String quarter, int priority) {}

    record CaseStudy(String company, String challenge, String solution, String result, String industry, String contactName) {}

    private static final List<Feature> features = List.of(
            new Feature("API Fluida", "Métodos en cadena intuitivos para código limpio y legible, reduciendo el tiempo de desarrollo hasta un 65%", 5, "Productividad"),
            new Feature("Fuentes Personalizadas", "Soporte para varios formatos incluyendo TTF, OTF con carga automática", 5, "Diseño"),
            new Feature("Tablas Dinámicas", "Creación flexible de tablas con amplias opciones de estilo y formato", 5, "Contenido"),
            new Feature("Gestión de Imágenes", "Soporte nativo de WebP con conversión automática y optimización", 4, "Multimedia"),
            new Feature("Personalización de Fondos", "Fondos de color e imagen con control de opacidad para documentos profesionales", 5, "Diseño"),
            new Feature("Fusión de PDFs", "Combina múltiples PDFs con facilidad manteniendo la integridad del documento", 4, "Productividad"),
            new Feature("Carga Inteligente", "Carga recursos desde URLs, classpath o sistema de archivos automáticamente", 5, "Técnica"),
            new Feature("Opciones de Estilo", "Formato de texto enriquecido, bordes personalizados y alineación precisa", 5, "Diseño"),
            new Feature("Soporte Multilingüe", "Gestión completa de caracteres Unicode y fuentes internacionales", 4, "Globalización"),
            new Feature("Alta Rendimiento", "Optimización de memoria y recursos para generación rápida de documentos", 5, "Técnica"),
            new Feature("Compatibilidad", "Funciona en todas las plataformas Java sin dependencias adicionales", 5, "Técnica"),
            new Feature("Seguridad", "Opciones para cifrado y protección de documentos confidenciales", 4, "Seguridad")
    );

    private static final List<Testimonial> testimonials = List.of(
            new Testimonial("TechSolutions S.A.", "DonPdf redujo nuestro tiempo de generación de documentos en un 60% mientras mejoraba significativamente la calidad de nuestros reportes corporativos. La transición fue extraordinariamente sencilla.", "Carlos Méndez", "Director de Tecnología", "Tecnología"),
            new Testimonial("DataViz Consultores", "La flexibilidad de DonPdf nos permitió crear informes personalizados que coinciden perfectamente con nuestra marca. Ahora podemos generar miles de documentos personalizados diariamente sin complicaciones.", "Laura Chen", "Desarrolladora Principal", "Consultoría"),
            new Testimonial("FinReport Systems", "Migramos de un sistema heredado complejo a DonPdf en solo dos semanas. La API fluida hizo que la transición fuera perfecta y nuestros desarrolladores pudieron adaptarse rápidamente.", "Miguel Rodríguez", "Gerente de Ingeniería", "Finanzas"),
            new Testimonial("Logística Global", "Antes tardábamos días en generar reportes de envíos personalizados. Con DonPdf, ahora lo hacemos en minutos con mejor calidad y consistencia en todos nuestros documentos corporativos.", "Ana González", "Directora de Operaciones", "Logística"),
            new Testimonial("HealthTech Innovators", "La capacidad de DonPdf para gestionar datos confidenciales de pacientes en informes seguros ha sido fundamental para nuestro cumplimiento normativo. Una herramienta excepcional para el sector salud.", "Javier Fernández", "Jefe de Seguridad Informática", "Salud")
    );

    private static final List<Metric> metrics = List.of(
            new Metric("Velocidad de Desarrollo", "65% Más Rápido", "+15%", true, "Reducción significativa en tiempo de implementación"),
            new Metric("Legibilidad del Código", "90% Aprobación", "+25%", true, "Mayor mantenibilidad y menor curva de aprendizaje"),
            new Metric("Costo de Mantenimiento", "40% Reducción", "-40%", true, "Menor necesidad de recursos para mantenimiento"),
            new Metric("Uso de Memoria", "30% Menos", "-30%", true, "Mejor rendimiento en entornos con recursos limitados"),
            new Metric("Tiempo de Integración", "75% Reducción", "-75%", true, "Implementación más rápida en sistemas existentes"),
            new Metric("Satisfacción del Desarrollador", "95% Positiva", "+35%", true, "Mayor productividad y menor fatiga del desarrollador")
    );

    private static final List<Comparison> comparisons = List.of(
            new Comparison("Diseño de API", "Patrón builder fluido con encadenamiento de métodos intuitivo que reduce la curva de aprendizaje", "Configuración de objetos compleja que requiere código verbose y propenso a errores"),
            new Comparison("Soporte de Imágenes", "Soporte nativo de WebP con conversión automática y amplio rango de formatos sin configuración adicional", "Soporte limitado de formatos que requiere conversión previa y configuración manual"),
            new Comparison("Carga de Recursos", "Carga inteligente desde múltiples fuentes (URL, classpath, sistema de archivos) con fallbacks automáticos", "Requisitos rígidos de ruta con resolución manual que complica el despliegue"),
            new Comparison("Gestión de Fuentes", "Integración sencilla de fuentes personalizadas con opciones de respaldo automáticas y detección inteligente", "Proceso complejo de registro de fuentes con personalización limitada y gestión manual"),
            new Comparison("Creación de Tablas", "Construcción dinámica de filas/celdas con opciones de estilo enriquecidas y gestión intuitiva", "Estructuras de tabla estáticas con control de estilo limitado y configuración verbosa")
    );

    private static final List<TechnicalBenefit> technicalBenefits = List.of(
            new TechnicalBenefit("Arquitectura basada en componentes", "Cada elemento del PDF es un componente autocontenido con propiedades encapsuladas", "Facilita pruebas unitarias y mantenimiento aislado"),
            new TechnicalBenefit("Gestión automática de recursos", "Cierre adecuado de recursos y gestión de memoria optimizada", "Previene fugas de memoria y mejora el rendimiento"),
            new TechnicalBenefit("Carga diferida de recursos", "Las imágenes y fuentes se cargan solo cuando son necesarias", "Reduce el consumo de memoria en documentos complejos"),
            new TechnicalBenefit("Conversión automática de formatos", "Manejo transparente de diferentes formatos de imagen y codificaciones", "Elimina la necesidad de preprocesamiento manual"),
            new TechnicalBenefit("Separación de contenido y presentación", "El contenido y los estilos se manejan por separado para mayor flexibilidad", "Permite temas reutilizables y consistencia visual")
    );

    private static final List<RoadmapItem> roadmapItems = List.of(
            new RoadmapItem("Firmas Digitales", "Implementación de firmas digitales con validación y certificación", "Q1 2026", 1),
            new RoadmapItem("Editor Visual", "Herramienta visual para diseñar plantillas de documentos", "Q2 2026", 2),
            new RoadmapItem("Optimización para Móviles", "Mejoras específicas para visualización en dispositivos móviles", "Q3 2026", 2),
            new RoadmapItem("Plantillas Avanzadas", "Sistema de plantillas con herencia y composición", "Q4 2026", 1),
            new RoadmapItem("Generación Asíncrona", "Procesamiento asíncrono para documentos de gran volumen", "Q2 2026", 3)
    );

    private static final List<CaseStudy> caseStudies = List.of(
            new CaseStudy(
                    "Banco Nacional de Innovación",
                    "Necesitaban generar miles de estados de cuenta personalizados diariamente con estrictos requisitos de seguridad y formateo.",
                    "Implementación de DonPdf con un sistema de plantillas personalizado y procesamiento paralelo para alta velocidad.",
                    "Reducción del tiempo de procesamiento en un 80%, cumplimiento total de normativas y mejora en la satisfacción del cliente.",
                    "Banca",
                    "Fernando Torres, Director de Sistemas"
            ),
            new CaseStudy(
                    "Distribuidora Continental",
                    "Sistema legacy que generaba reportes de inventario y facturación con errores frecuentes y apariencia inconsistente.",
                    "Migración a DonPdf con nuevas plantillas corporativas y validación automática de datos.",
                    "Eliminación de errores en documentos, reducción de 90% en quejas por facturación y mejora en la imagen corporativa.",
                    "Logística",
                    "Elena Ramírez, CIO"
            )
    );

    private static final Map<String, String> internationalUsage = Map.of(
            "América del Norte", "34%",
            "Europa", "28%",
            "Asia", "22%",
            "América Latina", "12%",
            "Oceanía", "3%",
            "África", "1%"
    );

    public static void main(String[] args) throws Exception {
        PdfFont titleFont = PdfFontFactory.createFont("fonts/JetBrainsMono-Bold.ttf");
        PdfFont headingFont = PdfFontFactory.createFont("fonts/JetBrainsMono-SemiBold.ttf");
        PdfFont normalFont = PdfFontFactory.createFont("fonts/JetBrainsMono-Regular.ttf");
        PdfFont lightFont = PdfFontFactory.createFont("fonts/JetBrainsMono-Light.ttf");
        PdfFont italicFont = PdfFontFactory.createFont("fonts/JetBrainsMono-Italic.ttf");
        PdfFont mediumFont = PdfFontFactory.createFont("fonts/JetBrainsMono-Medium.ttf");
        PdfFont emojiFont = PdfFontFactory.createFont("fonts/NotoEmoji-Regular.ttf");

        DeviceRgb primaryColor = new DeviceRgb(41, 128, 185); // Azul
        DeviceRgb secondaryColor = new DeviceRgb(52, 73, 94); // Azul oscuro grisáceo
        DeviceRgb accentColor = new DeviceRgb(46, 204, 113); // Verde
        DeviceRgb lightGray = new DeviceRgb(236, 240, 241);
        DeviceRgb darkGray = new DeviceRgb(52, 73, 94);
        DeviceRgb warningColor = new DeviceRgb(231, 76, 60); // Rojo

        DonPdf report = DonPdf.builder()
                .output("output/don_pdf_marketing_report_es.pdf")
                .pageSize(PageSize.A4)
                .defaultFont("fonts/JetBrainsMono-Regular.ttf")
                .defaultFontSize(11f)

                // Portada
                .backgroundImage("images/jaac-pdf.png")
                .backgroundOpacity(0.12f)

                // Logo
                .addImage()
                .path("images/jaac-pdf.png")
                .size(200f, 100f)
                .alignment(HorizontalAlignment.CENTER)
                .margins(50, 0, 0, 0)
                .next()

                // Título principal
                .addText()
                .content("DonPdf")
                .font(titleFont)
                .fontSize(48f)
                .color(primaryColor)
                .alignment(TextAlignment.CENTER)
                .margins(30, 0, 0, 0)
                .next()

                // Subtítulo
                .addText()
                .content("Biblioteca Revolucionaria para Generación de PDFs")
                .font(headingFont)
                .fontSize(20f)
                .color(secondaryColor)
                .alignment(TextAlignment.CENTER)
                .margins(15, 0, 0, 0)
                .next()

                // Eslogan
                .addText()
                .content("Simplicidad. Flexibilidad. Rendimiento.")
                .font(italicFont)
                .fontSize(16f)
                .color(darkGray)
                .alignment(TextAlignment.CENTER)
                .margins(25, 0, 0, 0)
                .next()

                // Versión
                .addText()
                .content("Versión 0.0.1 | 2025")
                .font(lightFont)
                .fontSize(14f)
                .color(darkGray)
                .alignment(TextAlignment.CENTER)
                .margins(240, 0, 0, 0)
                .next()

                // Página de Introducción
                .addText()
                .content("Resumen Ejecutivo")
                .font(titleFont)
                .fontSize(28f)
                .color(primaryColor)
                .alignment(TextAlignment.LEFT)
                .margins(50, 40, 20, 40)
                .next()

                .addText()
                .content("""
                        DonPdf representa un cambio de paradigma en la generación de documentos PDF para aplicaciones Java. 
                        Construida sobre la sólida base de iText, DonPdf introduce una revolucionaria API fluida que transforma 
                        la creación de documentos complejos en un proceso intuitivo y mantenible.
                        
                        Nuestra biblioteca aborda los puntos débiles comunes que los desarrolladores enfrentan con la generación 
                        tradicional de PDF: código verboso, configuración compleja y flexibilidad limitada. Con DonPdf, puede 
                        crear documentos sofisticados con menos código, mejor legibilidad y mantenibilidad mejorada.
                        
                        DonPdf ha sido diseñado desde cero pensando en el desarrollador moderno, ofreciendo una experiencia de 
                        programación superior que reduce drásticamente el tiempo de desarrollo mientras mejora la calidad del 
                        resultado final. Ya sea generando facturas, informes, folletos promocionales o cualquier otro tipo de 
                        documento, DonPdf simplifica todo el proceso sin sacrificar opciones avanzadas.
                        """)
                .font(normalFont)
                .fontSize(11f)
                .alignment(TextAlignment.JUSTIFIED)
                .margins(0, 40, 20, 40)
                .next()

                // Tabla de Métricas Clave
                .addText()
                .content("Indicadores Clave de Rendimiento")
                .font(headingFont)
                .fontSize(20f)
                .color(primaryColor)
                .alignment(TextAlignment.LEFT)
                .margins(20, 40, 10, 40)
                .next();

        // Tabla de Métricas Dinámica con colspan/rowspan
        TableBuilder metricsTable = report
                .addTable(220, 150, 150)
                .alignment(HorizontalAlignment.CENTER)
                .margins(0, 40, 30, 40)
                .border(new DashedBorder(lightGray, 1))
                .addRow()
                .addCell().content("Métrica").font(headingFont).fontSize(12f).color(lightGray).background(primaryColor).alignmentCenter()
                .addCell().content("Valor").font(headingFont).fontSize(12f).color(lightGray).background(primaryColor).alignmentCenter()
                .addCell().content("Interanual").font(headingFont).fontSize(12f).color(lightGray).background(primaryColor).alignmentCenter()
                .endRow();

        for (int i = 0; i < metrics.size(); i++) {
            Metric metric = metrics.get(i);
            DeviceRgb changeColor = metric.isPositive ? accentColor : warningColor;

            if (i == 2) { // Añadir una fila con colspan para destacar información
                metricsTable
                        .addRow()
                        .addCell().colspan(3).content("Impacto en Productividad y Recursos").font(mediumFont).fontSize(13f).alignmentCenter().background(lightGray)
                        .endRow();
            }

            metricsTable
                    .addRow()
                    .addCell().content(metric.name).font(normalFont).fontSize(11f).alignmentLeft()
                    .addCell().content(metric.value).font(headingFont).fontSize(11f).alignmentCenter()
                    .addCell().content(metric.change).font(normalFont).fontSize(11f).alignmentCenter().color(changeColor)
                    .endRow();

            // Añadir una fila de explicación con colspan
            if (i % 2 == 0) {
                metricsTable
                        .addRow()
                        .addCell().colspan(3).content(metric.impact).font(lightFont).fontSize(10f).alignmentCenter().color(darkGray)
                        .endRow();
            }
        }

        report = metricsTable.next()

                // Sección de Características
                .addText()
                .content("Características Principales")
                .font(titleFont)
                .fontSize(28f)
                .color(primaryColor)
                .alignment(TextAlignment.LEFT)
                .margins(50, 40, 20, 40)
                .next()

                .addText()
                .content("""
                        DonPdf ofrece una suite completa de características diseñadas para simplificar la generación de documentos 
                        mientras proporciona la flexibilidad necesaria para escenarios complejos. Nuestra biblioteca destaca por sus 
                        innovaciones que abordan los desafíos reales del desarrollo profesional de software.
                        
                        La filosofía de diseño de DonPdf se centra en proporcionar una API intuitiva que permita a los desarrolladores 
                        crear documentos complejos con mínimo esfuerzo, manteniendo un control granular sobre todos los aspectos de la 
                        generación de PDF cuando sea necesario. Esta combinación de simplicidad y potencia es lo que hace a DonPdf único 
                        en el ecosistema de bibliotecas Java.
                        """)
                .font(normalFont)
                .fontSize(11f)
                .alignment(TextAlignment.JUSTIFIED)
                .margins(0, 40, 20, 40)
                .next();

        // Tabla de Características Dinámica con categorías agrupadas
        TableBuilder featuresTable = report
                .addTable(150, 300, 50)
                .alignment(HorizontalAlignment.CENTER)
                .margins(0, 40, 30, 40)
                .addRow()
                .addCell().content("Característica").font(headingFont).fontSize(12f).color(lightGray).background(secondaryColor).alignmentCenter()
                .addCell().content("Descripción").font(headingFont).fontSize(12f).color(lightGray).background(secondaryColor).alignmentCenter()
                .addCell().content("Valoración").font(headingFont).fontSize(12f).color(lightGray).background(secondaryColor).alignmentCenter()
                .endRow();

        String currentCategory = "";
        for (Feature feature : features) {
            if (!currentCategory.equals(feature.category)) {
                currentCategory = feature.category;
                featuresTable
                        .addRow()
                        .addCell().colspan(3).content("CATEGORÍA: " + currentCategory).font(mediumFont).fontSize(12f).alignmentCenter().background(lightGray).color(primaryColor)
                        .endRow();
            }

            StringBuilder stars = new StringBuilder();
            for (int i = 0; i < feature.rating; i++) {
                stars.append("⭐");
            }

            featuresTable
                    .addRow()
                    .addCell().content(feature.name).font(headingFont).fontSize(11f).alignmentLeft()
                    .addCell().content(feature.description).font(normalFont).fontSize(11f).alignmentJustified()
                    .addCell().content(stars.toString()).font(emojiFont).fontSize(11f).alignmentCenter().color(accentColor)
                    .endRow();
        }

        report = featuresTable.next()

                // Imagen destacada de características
                .addImage()
                .path("images/jaac-pdf.png")
                .size(350f, 175f)
                .alignment(HorizontalAlignment.CENTER)
                .margins(0, 0, 40, 0)
                .border(new SolidBorder(lightGray, 1))
                .next()

                // Sección de Ventajas Técnicas
                .addText()
                .content("Ventajas Técnicas")
                .font(titleFont)
                .fontSize(24f)
                .color(primaryColor)
                .alignment(TextAlignment.LEFT)
                .margins(40, 40, 20, 40)
                .next()

                .addText()
                .content("""
                        DonPdf no solo ofrece una API elegante, sino que también incorpora numerosas optimizaciones técnicas 
                        que resultan en un rendimiento superior y mayor facilidad de mantenimiento. La arquitectura interna 
                        ha sido cuidadosamente diseñada para maximizar la eficiencia sin sacrificar la flexibilidad.
                        """)
                .font(normalFont)
                .fontSize(11f)
                .alignment(TextAlignment.JUSTIFIED)
                .margins(0, 40, 20, 40)
                .next();

        // Tabla de Beneficios Técnicos con rowspan
        TableBuilder technicalBenefitsTable = report
                .addTable(150, 200, 150)
                .alignment(HorizontalAlignment.CENTER)
                .margins(0, 40, 30, 40)
                .addRow()
                .addCell().content("Beneficio").font(headingFont).fontSize(12f).color(lightGray).background(primaryColor).alignmentCenter()
                .addCell().content("Explicación").font(headingFont).fontSize(12f).color(lightGray).background(primaryColor).alignmentCenter()
                .addCell().content("Impacto en Desarrollo").font(headingFont).fontSize(12f).color(lightGray).background(primaryColor).alignmentCenter()
                .endRow();

        for (int i = 0; i < technicalBenefits.size(); i++) {
            TechnicalBenefit benefit = technicalBenefits.get(i);

            if (i % 2 == 0 && i < technicalBenefits.size() - 1) { // Para beneficios pares, usar rowspan
                TechnicalBenefit nextBenefit = technicalBenefits.get(i + 1);

                technicalBenefitsTable
                        .addRow()
                        .addCell().rowspan(2).content(benefit.benefit() + "\n\n" + nextBenefit.benefit()).font(mediumFont).fontSize(11f).alignmentLeft().color(secondaryColor)
                        .addCell().content(benefit.explanation()).font(normalFont).fontSize(11f).alignmentJustified()
                        .addCell().content(benefit.developmentImpact()).font(lightFont).fontSize(11f).alignmentLeft()
                        .endRow()
                        .addRow()
                        .addCell().content(nextBenefit.explanation()).font(normalFont).fontSize(11f).alignmentJustified()
                        .addCell().content(nextBenefit.developmentImpact()).font(lightFont).fontSize(11f).alignmentLeft()
                        .endRow();

                i++; // Saltar el siguiente beneficio ya que lo incluimos
            } else {
                technicalBenefitsTable
                        .addRow()
                        .addCell().content(benefit.benefit()).font(mediumFont).fontSize(11f).alignmentLeft().color(secondaryColor)
                        .addCell().content(benefit.explanation()).font(normalFont).fontSize(11f).alignmentJustified()
                        .addCell().content(benefit.developmentImpact()).font(lightFont).fontSize(11f).alignmentLeft()
                        .endRow();
            }
        }

        report = technicalBenefitsTable.next()

                // Sección de Comparativa
                .addText()
                .content("Ventajas Competitivas")
                .font(titleFont)
                .fontSize(28f)
                .color(primaryColor)
                .alignment(TextAlignment.LEFT)
                .margins(50, 40, 20, 40)
                .next()

                .addText()
                .content("""
                        Cuando se compara con las bibliotecas tradicionales de generación de PDF, DonPdf ofrece ventajas significativas 
                        que se traducen en ganancias reales de productividad y mejoras en la calidad del código. La siguiente comparación 
                        destaca las áreas clave donde DonPdf sobresale.
                        
                        Estas diferencias no son meramente teóricas; están basadas en escenarios reales de desarrollo donde DonPdf ha 
                        demostrado consistentemente ofrecer una experiencia superior tanto para desarrolladores como para usuarios finales 
                        de los documentos generados.
                        """)
                .font(normalFont)
                .fontSize(11f)
                .alignment(TextAlignment.JUSTIFIED)
                .margins(0, 40, 20, 40)
                .next();

        // Tabla de Comparación Dinámica
        TableBuilder comparisonTable = report
                .addTable(120, 240, 240)
                .alignment(HorizontalAlignment.CENTER)
                .margins(0, 40, 30, 40)
                .addRow()
                .addCell().content("Aspecto").font(headingFont).fontSize(12f).color(lightGray).background(secondaryColor).alignmentCenter()
                .addCell().content("Ventaja DonPdf").font(headingFont).fontSize(12f).color(lightGray).background(primaryColor).alignmentCenter()
                .addCell().content("Bibliotecas Tradicionales").font(headingFont).fontSize(12f).color(lightGray).background(darkGray).alignmentCenter()
                .endRow();

        for (Comparison comparison : comparisons) {
            comparisonTable
                    .addRow()
                    .addCell().content(comparison.aspect).font(headingFont).fontSize(11f).alignmentLeft()
                    .addCell().content(comparison.donPdfAdvantage).font(normalFont).fontSize(11f).alignmentLeft().color(primaryColor)
                    .addCell().content(comparison.competitorLimitation).font(lightFont).fontSize(11f).alignmentLeft()
                    .endRow();
        }

        // Añadir una fila de conclusión con colspan
        comparisonTable
                .addRow()
                .addCell().colspan(3).content("""
                        Estas ventajas se traducen en una experiencia de desarrollo significativamente mejor, con código más limpio, 
                        menor tiempo de implementación y resultados finales de mayor calidad. DonPdf elimina la complejidad innecesaria 
                        para que pueda centrarse en lo que realmente importa: el contenido de sus documentos.
                        """).font(italicFont).fontSize(11f).alignmentCenter().color(secondaryColor)
                .endRow();

        report = comparisonTable.next()

                // Sección de Adopción Global
                .addText()
                .content("Adopción Global de DonPdf")
                .font(titleFont)
                .fontSize(24f)
                .color(primaryColor)
                .alignment(TextAlignment.LEFT)
                .margins(40, 40, 20, 40)
                .next()

                .addText()
                .content("""
                        DonPdf ha sido adoptado por organizaciones de todo el mundo, desde startups hasta empresas Fortune 500. 
                        La distribución geográfica muestra una rápida adopción en todas las regiones, con particular fuerza en 
                        América del Norte y Europa, y un crecimiento acelerado en Asia y América Latina.
                        """)
                .font(normalFont)
                .fontSize(11f)
                .alignment(TextAlignment.JUSTIFIED)
                .margins(0, 40, 20, 40)
                .next();

        // Tabla de uso internacional con colspan y rowspan
        TableBuilder internationalTable = report
                .addTable(180, 180, 140)
                .alignment(HorizontalAlignment.CENTER)
                .margins(0, 40, 30, 40)
                .border(new SolidBorder(lightGray, 1))
                .addRow()
                .addCell().content("Región").font(headingFont).fontSize(12f).color(lightGray).background(secondaryColor).alignmentCenter()
                .addCell().content("Porcentaje de Uso").font(headingFont).fontSize(12f).color(lightGray).background(secondaryColor).alignmentCenter()
                .addCell().content("Tendencia").font(headingFont).fontSize(12f).color(lightGray).background(secondaryColor).alignmentCenter()
                .endRow()
                .addRow()
                .addCell().rowspan(2).content("Principales Mercados").font(mediumFont).fontSize(12f).alignmentCenter().background(lightGray)
                .addCell().content("América del Norte: " + internationalUsage.get("América del Norte")).font(normalFont).fontSize(11f).alignmentLeft()
                .addCell().content("Crecimiento sostenido").font(lightFont).fontSize(11f).alignmentCenter().color(accentColor)
                .endRow()
                .addRow()
                .addCell().content("Europa: " + internationalUsage.get("Europa")).font(normalFont).fontSize(11f).alignmentLeft()
                .addCell().content("Adopción acelerada").font(lightFont).fontSize(11f).alignmentCenter().color(accentColor)
                .endRow()
                .addRow()
                .addCell().rowspan(2).content("Mercados Emergentes").font(mediumFont).fontSize(12f).alignmentCenter().background(lightGray)
                .addCell().content("Asia: " + internationalUsage.get("Asia")).font(normalFont).fontSize(11f).alignmentLeft()
                .addCell().content("Crecimiento rápido").font(lightFont).fontSize(11f).alignmentCenter().color(accentColor)
                .endRow()
                .addRow()
                .addCell().content("América Latina: " + internationalUsage.get("América Latina")).font(normalFont).fontSize(11f).alignmentLeft()
                .addCell().content("Adopción en aumento").font(lightFont).fontSize(11f).alignmentCenter().color(accentColor)
                .endRow()
                .addRow()
                .addCell().colspan(3).content("DonPdf es utilizado actualmente en más de 67 países y está disponible en 12 idiomas.").font(italicFont).fontSize(11f).alignmentCenter().color(secondaryColor)
                .endRow();

        report = internationalTable.next()

                // Imagen ilustrativa
                .addImage()
                .path("images/jaac-pdf.png")
                .size(300f, 150f)
                .alignment(HorizontalAlignment.CENTER)
                .margins(10, 0, 30, 0)
                .next()

                // Sección de Testimonios
                .addText()
                .content("Casos de Éxito")
                .font(titleFont)
                .fontSize(28f)
                .color(primaryColor)
                .alignment(TextAlignment.LEFT)
                .margins(50, 40, 20, 40)
                .next()

                .addText()
                .content("""
                        Organizaciones de diversas industrias han experimentado mejoras significativas después de adoptar DonPdf. 
                        Estos testimonios muestran el impacto real que nuestra biblioteca ha tenido en entornos de producción exigentes.
                        """)
                .font(normalFont)
                .fontSize(11f)
                .alignment(TextAlignment.JUSTIFIED)
                .margins(0, 40, 20, 40)
                .next();

        // Testimonios dinámicos con diseño mejorado
        for (int i = 0; i < testimonials.size(); i++) {
            Testimonial testimonial = testimonials.get(i);

            // Alternar colores para los testimonios
            DeviceRgb quoteColor = (i % 2 == 0) ? secondaryColor : primaryColor;

            report
                    .addText()
                    .content("\"" + testimonial.quote + "\"")
                    .font(italicFont)
                    .fontSize(13f)
                    .color(quoteColor)
                    .alignment(TextAlignment.LEFT)
                    .margins(10, 60, 5, 60)
                    .border(new SolidBorder(lightGray, 1))
                    .next()
                    .addText()
                    .content("— " + testimonial.author + ", " + testimonial.position + " | " + testimonial.company + " (" + testimonial.industry + ")")
                    .font(lightFont)
                    .fontSize(11f)
                    .alignment(TextAlignment.RIGHT)
                    .margins(0, 60, 30, 60)
                    .next();
        }

        // Sección de casos de estudio detallados
        report
                .addText()
                .content("Casos de Estudio Detallados")
                .font(titleFont)
                .fontSize(24f)
                .color(primaryColor)
                .alignment(TextAlignment.LEFT)
                .margins(40, 40, 20, 40)
                .next();

        // Tabla de casos de estudio con colspan/rowspan
        TableBuilder caseStudiesTable = report
                .addTable(500)
                .alignment(HorizontalAlignment.CENTER)
                .margins(0, 40, 30, 40);

        for (CaseStudy caseStudy : caseStudies) {
            caseStudiesTable
                    .addRow()
                    .addCell().content(caseStudy.company() + " (" + caseStudy.industry() + ")").font(headingFont).fontSize(14f).color(lightGray).background(primaryColor).alignmentCenter()
                    .endRow()
                    .addRow()
                    .addCell().content("DESAFÍO").font(mediumFont).fontSize(12f).alignmentLeft().color(secondaryColor)
                    .endRow()
                    .addRow()
                    .addCell().content(caseStudy.challenge()).font(normalFont).fontSize(11f).alignmentJustified()
                    .endRow()
                    .addRow()
                    .addCell().content("SOLUCIÓN").font(mediumFont).fontSize(12f).alignmentLeft().color(primaryColor)
                    .endRow()
                    .addRow()
                    .addCell().content(caseStudy.solution()).font(normalFont).fontSize(11f).alignmentJustified()
                    .endRow()
                    .addRow()
                    .addCell().content("RESULTADO").font(mediumFont).fontSize(12f).alignmentLeft().color(accentColor)
                    .endRow()
                    .addRow()
                    .addCell().content(caseStudy.result()).font(normalFont).fontSize(11f).alignmentJustified()
                    .endRow()
                    .addRow()
                    .addCell().content("Contacto: " + caseStudy.contactName()).font(lightFont).fontSize(10f).alignmentRight()
                    .endRow();
        }

        report = caseStudiesTable.next()

                // Sección de Implementación Técnica
                .addText()
                .content("Implementación Técnica")
                .font(titleFont)
                .fontSize(28f)
                .color(primaryColor)
                .alignment(TextAlignment.LEFT)
                .margins(50, 40, 20, 40)
                .next()

                .addText()
                .content("""
                        La elegancia de DonPdf se demuestra mejor a través de ejemplos de código. Compare el enfoque tradicional 
                        con la API fluida de DonPdf para ver la mejora dramática en legibilidad y mantenibilidad.
                        
                        Un desarrollador experimentado puede reducir hasta un 70% la cantidad de código necesario para generar documentos 
                        complejos, mientras que un desarrollador nuevo puede comenzar a ser productivo en cuestión de horas, no días.
                        """)
                .font(normalFont)
                .fontSize(11f)
                .alignment(TextAlignment.JUSTIFIED)
                .margins(0, 40, 20, 40)
                .next()

                .addText()
                .content("Implementación con DonPdf:")
                .font(headingFont)
                .fontSize(16f)
                .color(secondaryColor)
                .alignment(TextAlignment.LEFT)
                .margins(10, 40, 5, 40)
                .next()

                .addText()
                .content("""
                        DonPdf.builder()
                            .output("informe.pdf")
                            .pageSize(PageSize.A4)
                            .backgroundImage("fondo.png")
                            .backgroundOpacity(0.15f)
                            .defaultFont("fonts/personalizada.ttf")
                            .addText()
                                .content("Título del Documento")
                                .fontSize(24f)
                                .alignment(TextAlignment.CENTER)
                                .margins(20, 20, 20, 20)
                            .next()
                            .addTable(100, 200)
                                .addRow()
                                    .addCell().content("Encabezado 1").background("#336699").color("WHITE")
                                    .addCell().content("Encabezado 2").background("#336699").color("WHITE")
                                .endRow()
                                .addRow()
                                    .addCell().content("Datos 1").fontSize(12f)
                                    .addCell().content("Datos 2").fontSize(12f)
                                .endRow()
                            .next()
                            .build();
                        """)
                .font(normalFont)
                .fontSize(11f)
                .color(new DeviceRgb(44, 62, 80))
                .border(new SolidBorder(lightGray, 1))
                .margins(0, 40, 20, 40)
                .next()

                .addText()
                .content("Componentes Avanzados:")
                .font(headingFont)
                .fontSize(16f)
                .color(secondaryColor)
                .alignment(TextAlignment.LEFT)
                .margins(10, 40, 5, 40)
                .next()

                .addText()
                .content("""
                        // Tablas complejas con colspan y rowspan
                        DonPdf.builder()
                            .addTable(100, 100, 200)
                                .addRow()
                                    .addCell().content("Cabecera").colspan(3).alignmentCenter()
                                .endRow()
                                .addRow()
                                    .addCell().content("Etiqueta").rowspan(2).alignmentCenter()
                                    .addCell().content("Valor 1").alignmentLeft()
                                    .addCell().content("Descripción 1").alignmentLeft()
                                .endRow()
                                .addRow()
                                    .addCell().content("Valor 2").alignmentLeft()
                                    .addCell().content("Descripción 2").alignmentLeft()
                                .endRow()
                            .next();
                        
                        // Manejo avanzado de imágenes
                        DonPdf.builder()
                            .addImage()
                                .path("logo.webp")  // Conversión automática WebP
                                .size(200, 100)
                                .alignment(HorizontalAlignment.CENTER)
                                .border(new SolidBorder(1))
                                .margins(20, 20, 20, 20)
                            .next();
                        """)
                .font(normalFont)
                .fontSize(11f)
                .color(new DeviceRgb(44, 62, 80))
                .border(new SolidBorder(lightGray, 1))
                .margins(0, 40, 20, 40)
                .next()

                // Imagen de muestra
                .addImage()
                .path("images/jaac-pdf.png")
                .size(350f, 175f)
                .alignment(HorizontalAlignment.CENTER)
                .margins(0, 0, 30, 0)
                .next()

                // Sección de Hoja de Ruta
                .addText()
                .content("Hoja de Ruta de DonPdf")
                .font(titleFont)
                .fontSize(24f)
                .color(primaryColor)
                .alignment(TextAlignment.LEFT)
                .margins(40, 40, 20, 40)
                .next()

                .addText()
                .content("""
                        Nuestro compromiso con la mejora continua se refleja en nuestra hoja de ruta. Estas son las características 
                        y mejoras planificadas para las próximas versiones de DonPdf que harán que nuestra biblioteca sea aún más 
                        potente y fácil de usar.
                        """)
                .font(normalFont)
                .fontSize(11f)
                .alignment(TextAlignment.JUSTIFIED)
                .margins(0, 40, 20, 40)
                .next();

        // Tabla de hoja de ruta con colores según prioridad
        TableBuilder roadmapTable = report
                .addTable(120, 240, 70, 70)
                .alignment(HorizontalAlignment.CENTER)
                .margins(0, 40, 30, 40)
                .addRow()
                .addCell().content("Característica").font(headingFont).fontSize(12f).color(lightGray).background(secondaryColor).alignmentCenter()
                .addCell().content("Descripción").font(headingFont).fontSize(12f).color(lightGray).background(secondaryColor).alignmentCenter()
                .addCell().content("Trimestre").font(headingFont).fontSize(12f).color(lightGray).background(secondaryColor).alignmentCenter()
                .addCell().content("Prioridad").font(headingFont).fontSize(12f).color(lightGray).background(secondaryColor).alignmentCenter()
                .endRow();

        for (RoadmapItem item : roadmapItems) {
            // Color basado en la prioridad
            DeviceRgb priorityColor;
            String priorityText;

            switch (item.priority()) {
                case 1:
                    priorityColor = warningColor;
                    priorityText = "Alta";
                    break;
                case 2:
                    priorityColor = primaryColor;
                    priorityText = "Media";
                    break;
                default:
                    priorityColor = accentColor;
                    priorityText = "Normal";
            }

            roadmapTable
                    .addRow()
                    .addCell().content(item.feature()).font(mediumFont).fontSize(11f).alignmentLeft()
                    .addCell().content(item.description()).font(normalFont).fontSize(11f).alignmentLeft()
                    .addCell().content(item.quarter()).font(normalFont).fontSize(11f).alignmentCenter()
                    .addCell().content(priorityText).font(normalFont).fontSize(11f).alignmentCenter().color(priorityColor)
                    .endRow();
        }

        // Fila con colspan para nota explicativa
        roadmapTable
                .addRow()
                .addCell().colspan(4).content("""
                        Nota: Esta hoja de ruta está sujeta a cambios basados en la retroalimentación de los usuarios y las necesidades del mercado. 
                        Nos comprometemos a mantener actualizados a nuestros usuarios sobre cualquier cambio en nuestros planes.
                        """).font(lightFont).fontSize(10f).alignmentCenter().color(darkGray)
                .endRow();

        report = roadmapTable.next()

                // Conclusión
                .addText()
                .content("¿Por qué elegir DonPdf?")
                .font(titleFont)
                .fontSize(28f)
                .color(primaryColor)
                .alignment(TextAlignment.LEFT)
                .margins(50, 40, 20, 40)
                .next()

                .addText()
                .content("""
                        DonPdf representa el futuro de la generación de PDF en aplicaciones Java. Al combinar el poder de iText 
                        con un diseño innovador de API fluida, hemos creado una biblioteca que mejora dramáticamente la productividad 
                        del desarrollador y la calidad del código.
                        
                        La diferencia entre DonPdf y otras bibliotecas no es solo una cuestión de sintaxis más limpia; es una filosofía 
                        completa de diseño centrada en el desarrollador que resulta en un código más mantenible, menos propenso a errores 
                        y significativamente más rápido de escribir y comprender.
                        
                        Ya sea que esté desarrollando una aplicación empresarial compleja o un proyecto más pequeño, DonPdf ofrece la 
                        combinación perfecta de simplicidad, flexibilidad y rendimiento que necesita para crear documentos PDF de alta calidad 
                        con un mínimo esfuerzo.
                        """)
                .font(normalFont)
                .fontSize(11f)
                .alignment(TextAlignment.JUSTIFIED)
                .margins(0, 40, 10, 40)
                .next()

                .addText()
                .content("Resumen de beneficios:")
                .font(headingFont)
                .fontSize(16f)
                .margins(10, 40, 5, 40)
                .next();

        // Tabla de beneficios con estilo visual mejorado
        TableBuilder benefitsTable = report
                .addTable(500)
                .alignment(HorizontalAlignment.CENTER)
                .margins(0, 40, 20, 40)
                .addRow()
                .addCell().content("✓ Reducción del tiempo de desarrollo hasta un 65% en comparación con bibliotecas tradicionales").alignmentLeft().font(normalFont)
                .endRow()
                .addRow()
                .addCell().content("✓ Mejora de la legibilidad y mantenibilidad del código con el patrón fluido de construcción").alignmentLeft().font(normalFont)
                .endRow()
                .addRow()
                .addCell().content("✓ Simplificación de la gestión de recursos con carga inteligente desde múltiples fuentes").alignmentLeft().font(normalFont)
                .endRow()
                .addRow()
                .addCell().content("✓ Mejora de la calidad del documento con opciones avanzadas de estilo y formato").alignmentLeft().font(normalFont)
                .endRow()
                .addRow()
                .addCell().content("✓ Disminución del tiempo de capacitación para nuevos desarrolladores con un diseño intuitivo").alignmentLeft().font(normalFont)
                .endRow()
                .addRow()
                .addCell().content("✓ Optimización automática del rendimiento y gestión eficiente de la memoria").alignmentLeft().font(normalFont)
                .endRow()
                .addRow()
                .addCell().content("✓ Soporte nativo para formatos modernos de imágenes y fuentes personalizadas").alignmentLeft().font(normalFont)
                .endRow();

        report = benefitsTable.next()

                // Llamada a la acción
                .addText()
                .content("Comience su viaje con DonPdf hoy mismo")
                .font(titleFont)
                .fontSize(22f)
                .color(accentColor)
                .alignment(TextAlignment.CENTER)
                .margins(20, 0, 10, 0)
                .next()

                .addText()
                .content("""
                        Visite nuestra documentación para descubrir cómo DonPdf puede transformar su flujo de trabajo de generación de documentos. 
                        Ofrecemos ejemplos completos, tutoriales detallados y soporte técnico para ayudarle a aprovechar al máximo nuestra biblioteca.
                        
                        Únase a las empresas líderes que ya han adoptado DonPdf como su solución preferida para la generación de documentos PDF en Java.
                        """)
                .font(normalFont)
                .fontSize(11f)
                .alignment(TextAlignment.CENTER)
                .margins(0, 40, 10, 40)
                .next()

                // Logo del pie de página
                .addImage()
                .path("images/jaac-pdf.png")
                .size(180f, 90f)
                .alignment(HorizontalAlignment.CENTER)
                .margins(40, 0, 20, 0)
                .next()

                // Texto del pie de página
                .addText()
                .content("© 2025 DonPdf - Generación Revolucionaria de PDF para Java")
                .font(lightFont)
                .fontSize(9f)
                .color(darkGray)
                .alignment(TextAlignment.CENTER)
                .margins(5, 0, 20, 0)
                .next();

        // Construir el PDF
        report.build();

        System.out.println("¡Reporte de Marketing de DonPdf generado exitosamente!");
    }
}
