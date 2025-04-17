# DonPdf

Una biblioteca simple para la creación de documentos PDF en Java,
visítanos en nuestra [web](https://www.donpdf.com).

## Requisitos

- Java 21 o superior
- Gradle 8.10 o superior

## Dependencias

- iText Core
- JavaCV Platform

## Características

- Creación de documentos PDF con texto, imágenes y tablas
- Soporte para fondos de color y fondos de imagen
- Carga de fuentes personalizadas
- Combinar múltiples documentos PDF
- Soporte para imágenes WebP (convertidas automáticamente a PNG)

## Instalación

Agrega la biblioteca como dependencia en tu proyecto:

```groovy
implementation 'com.donpdf.pdf:don-pdf:0.0.4'
```

```xml
<dependency>
  <groupId>com.donpdf.pdf</groupId>
  <artifactId>don-pdf</artifactId>
  <version>0.0.4</version>
</dependency>
```

## Ejemplos

### Documento simple con texto

```java
import main.com.donpdf.pdf.DonPdf;

public class SimpleTextExample {
    public static void main(String[] args) {
        DonPdf.builder()
            .output("output/texto-simple.pdf")
            .addText()
                .content("Hola Mundo!")
                .fontSize(16)
                .next()
            .build();
        System.out.println("PDF generado correctamente.");
    }
}
```

### Texto con formato personalizado

```java
import main.com.donpdf.pdf.DonPdf;

public class FormattedTextExample {
    public static void main(String[] args) {
        DonPdf.builder()
            .output("output/texto-con-formato.pdf")
            .addText()
                .content("Texto con formato")
                .fontSize(18)
                .alignmentCenter()
                .color("BLUE")
                .margins(10, 5, 10, 5)
                .next()
            .addText()
                .content("Este texto usa color RGB.")
                .fontSize(14)
                .color(255, 0, 0)  // Rojo
                .next()
            .build();
        System.out.println("PDF con formato generado correctamente.");
    }
}
```

### Usar una fuente personalizada

```java
import main.com.donpdf.pdf.DonPdf;

public class CustomFontExample {
    public static void main(String[] args) {
        DonPdf.builder()
            .output("output/fuente-personalizada.pdf")
            .defaultFont("fonts/roboto.ttf")
            .defaultFontSize(14)
            .addText()
                .content("Texto con fuente personalizada")
                .next()
            .addText()
                .content("Cambio de fuente en un texto específico")
                .font("fonts/montserrat.ttf")
                .next()
            .build();
        System.out.println("PDF con fuente personalizada generado correctamente.");
    }
}
```

### Documento con fondo de color

```java
import main.com.donpdf.pdf.DonPdf;

public class ColorBackgroundExample {
    public static void main(String[] args) {
        DonPdf.builder()
            .output("output/fondo-color.pdf")
            .backgroundColor("blue")
            .addText()
                .content("Documento con fondo de color")
                .fontSize(16)
                .color("NAVY")
                .next()
            .build();
        System.out.println("PDF con fondo de color generado correctamente.");
    }
}
```

### Documento con imagen de fondo

```java
import main.com.donpdf.pdf.DonPdf;

public class ImageBackgroundExample {
    public static void main(String[] args) {
        DonPdf.builder()
            .output("output/imagen-fondo.pdf")
            .backgroundImage("images/background.jpg")
            .backgroundOpacity(0.3f)  // Opacidad para que se vea el texto
            .addText()
                .content("Documento con imagen de fondo")
                .fontSize(20)
                .alignmentCenter()
                .next()
            .build();
        System.out.println("PDF con imagen de fondo generado correctamente.");
    }
}
```

### Añadir una imagen

```java
import main.com.donpdf.pdf.DonPdf;
import com.itextpdf.layout.properties.HorizontalAlignment;

public class AddImageExample {
    public static void main(String[] args) {
         DonPdf.builder()
             .output("output/con-imagen.pdf")
             .addText()
                 .content("Documento con una imagen")
                 .fontSize(16)
                 .alignmentCenter()
                 .next()
             .addImage()
                 .path("images/logo.png")
                 .size(200, 100)  // Ancho y alto en puntos
                 .alignment(HorizontalAlignment.CENTER)
                 .margins(10, 0, 10, 0)
                 .next()
             .build();
         System.out.println("PDF con imagen generado correctamente.");
    }
}
```

### Plantilla HTML/CSS a PDF

```html
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Reporte Financiero</title>
  <style>....</style>
</head>
<body>
<div class="container">
  <div class="header">
    <div class="logo-container">
      LOGO
    </div>
    <div class="report-title">
      <h1>REPORTE FINANCIERO</h1>
      <p>Período: {{periodo_inicio}} - {{periodo_fin}}</p>
      <p>Generado: {{fecha_generación}}</p>
      <p>Referencia: {{número_referencia}}</p>
    </div>
  </div>
  ...continuar con la platilla
</div>
```

```java
import com.donpdf.pdf.main.DonPdf;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Html {
    public static void main(String[] args) throws Exception {
        Map<String, String> template = new HashMap<>();
        template.put("periodo_inicio", "2025-01");
        template.put("periodo_fin", "2025-05");
        template.put("fecha_generación", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        template.put("número_referencia", "943167");
        template.put("cliente_nombre", "Lucho Gonzales");
        template.put("cliente_numero", "987654321");
        template.put("cuenta_principal", "Enrique Manuel");
        template.put("tipo_cuenta", "Premium");
        template.put("cliente_dirección", "Av. Lomas del Polar, Juan Belau");
        template.put("cliente_telefono", "01 0164 46124");
        template.put("saldo_actual", "$ 106.130.00");
        template.put("saldo_cambio", "57");
        template.put("ingresos_periodo", "$ 10.640.00");
        template.put("ingresos_cambio", "34");
        template.put("gastos_periodo", "$ 5.613.00");
        template.put("gastos_cambio", "28");
        DonPdf.builder()
                .output("output/main.pdf")
                .addHtml()
                .path("html/main.html")
                .template(template)
                .build();
    }
}
```

### Crear una tabla simple

```java
import main.com.donpdf.pdf.DonPdf;
import com.itextpdf.layout.properties.HorizontalAlignment;

public class SimpleTableExample {
    public static void main(String[] args) {
         DonPdf.builder()
             .output("output/tabla-simple.pdf")
             .addText()
                 .content("Ejemplo de tabla")
                 .fontSize(16)
                 .alignmentCenter()
                 .margins(0, 0, 10, 0)
                 .next()
             .addTable(150, 150, 150)  // 3 columnas de 150 puntos cada una
                 .width(450)  // Ancho total de la tabla
                 .alignment(HorizontalAlignment.CENTER)
                 .addRow()
                     .addCell()
                         .content("Nombre")
                         .alignmentCenter()
                         .background("LIGHTGRAY")
                     .addCell()
                         .content("Edad")
                         .alignmentCenter()
                         .background("LIGHTGRAY")
                     .addCell()
                         .content("Email")
                         .alignmentCenter()
                         .background("LIGHTGRAY")
                         .endRow()
                 .addRow()
                     .addCell()
                         .content("Juan Pérez")
                     .addCell()
                         .content("28")
                         .alignmentCenter()
                     .addCell()
                         .content("juan@example.com")
                         .endRow()
                 .addRow()
                     .addCell()
                         .content("Ana Gómez")
                     .addCell()
                         .content("34")
                         .alignmentCenter()
                     .addCell()
                         .content("ana@example.com")
                         .endRow()
                 .next()
             .build();
         System.out.println("PDF con tabla simple generado correctamente.");
    }
}
```

### Generar tabla dinámicamente

```java
import main.com.donpdf.pdf.DonPdf;
import builder.com.donpdf.pdf.TableBuilder;
import com.itextpdf.layout.properties.HorizontalAlignment;

import java.util.Arrays;
import java.util.List;

public class DynamicTableExample {
    static class Person {
        private final String name;
        private final int age;
        private final String email;

        public Person(String name, int age, String email) {
            this.name = name;
            this.age = age;
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public String getEmail() {
            return email;
        }
    }
    
    public static void main(String[] args) {
         List<Person> personas = Arrays.asList(
             new Person("Juan Pérez", 28, "juan@example.com"),
             new Person("Ana Gómez", 34, "ana@example.com"),
             new Person("Carlos Ruiz", 45, "carlos@example.com"),
             new Person("María López", 31, "maria@example.com")
         );
         // Crear PDF con tabla dinámica
         DonPdf donPdf = DonPdf.builder()
             .output("output/tabla-dinámica.pdf")
             .addText()
                 .content("Tabla Generada Dinámicamente")
                 .fontSize(16)
                 .alignmentCenter()
                 .margins(0, 0, 10, 0)
                 .next();
         // Crear la tabla
         TableBuilder tableBuilder = donPdf.addTable(150, 100, 200)
             .width(450)
             .alignment(HorizontalAlignment.CENTER);
         // Añadir encabezados
         tableBuilder.addRow()
             .addCell()
                 .content("Nombre")
                 .alignmentCenter()
                 .background("gray")
             .addCell()
                 .content("Edad")
                 .alignmentCenter()
                 .background("gray")
             .addCell()
                 .content("Email")
                 .alignmentCenter()
                 .background("gray")
                 .endRow();
         // Añadir filas dinámicamente con un bucle for
         for (Person persona : personas) {
             tableBuilder.addRow()
                 .addCell()
                     .content(persona.getName())
                 .addCell()
                     .content(String.valueOf(persona.getAge()))
                     .alignmentCenter()
                 .addCell()
                     .content(persona.getEmail())
                     .endRow();
         }
         // Finalizar tabla y construir PDF
         tableBuilder.next().build();
         System.out.println("PDF con tabla dinámica generado correctamente.");
    }
}
```

### Combinar múltiples PDFs

```java
import main.com.donpdf.pdf.DonPdf;

public class MergePdfExample {
    public static void main(String[] args) {
        // Crear un PDF inicial
        DonPdf.builder()
            .output("output/parte1.pdf")
            .addText()
                .content("Primera parte del documento")
                .fontSize(16)
                .next()
            .build();
        // Crear un segundo PDF
        DonPdf.builder()
            .output("output/parte2.pdf")
            .addText()
                .content("Segunda parte del documento")
                .fontSize(16)
                .next()
            .build();
        // Combinar ambos PDFs
        DonPdf.builder()
            .output("output/combinado.pdf")
            .addText()
                .content("Documento combinado")
                .fontSize(18)
                .next()
            .mergePdf("output/parte1.pdf")
            .mergePdf("output/parte2.pdf")
            .build();
        System.out.println("PDFs combinados correctamente.");
    }
}
```

### Ejemplo completo: Reporte de ventas

```java
import com.jaac.pdf.builder.PdfBuilder;
import builder.com.donpdf.pdf.TableBuilder;
import builder.com.donpdf.pdf.RowBuilder;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.borders.SolidBorder;

import java.util.List;
import java.util.Arrays;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SalesReportExample {
    public static void main(String[] args) {
         LocalDate today = LocalDate.now();
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
         List<Sale> sales = Arrays.asList(
             new Sale("Laptop HP", 2, 1200.00),
             new Sale("Monitor Dell", 3, 350.00),
             new Sale("Teclado Logitech", 5, 85.50),
             new Sale("Mouse Logitech", 5, 45.99),
             new Sale("Impresora Epson", 1, 399.99)
         );
         // Calcular totales
         double totalAmount = sales
                 .stream()
                 .mapToDouble(sale -> sale.getQuantity() * sale.getUnitPrice())
                 .sum();
         // Crear el PDF
         DonPdf.builder()
             .output("output/reporte-ventas.pdf")
             .backgroundImage("images/watermark.jpg")
             .backgroundOpacity(0.1f)
             .addText()
                 .content("REPORTE DE VENTAS")
                 .fontSize(20)
                 .alignmentCenter()
                 .margins(20, 0, 5, 0)
                 .next()
             .addText()
                 .content("Fecha: " + today.format(formatter))
                 .fontSize(12)
                 .alignmentRight()
                 .margins(0, 10, 20, 0)
                 .next()
             .addTable(200, 100, 150)
                 .width(450)
                 .alignment(HorizontalAlignment.CENTER)
                 .margins(20, 10, 10, 10)
                 .addRow()
                     .addCell()
                         .content("Producto")
                         .fontSize(12)
                         .background("gray")
                         .alignmentCenter()
                     .addCell()
                         .content("Cantidad")
                         .fontSize(12)
                         .background("gray")
                         .alignmentCenter()
                     .addCell()
                         .content("Precio Total")
                         .fontSize(12)
                         .background("gray")
                         .alignmentCenter()
                         .endRow()
                 // Generar filas de ventas dinámicamente
                 .addDynamicSalesRows(sales)
                 // Añadir fila de total
                 .addRow()
                     .addCell()
                         .content("TOTAL")
                         .fontSize(12)
                         .background("blue")
                         .alignmentRight()
                     .addCell()
                         .content("")
                         .background("blue")
                     .addCell()
                         .content(String.format("$%.2f", totalAmount))
                         .fontSize(12)
                         .background("blue")
                         .alignmentRight()
                         .endRow()
                 .next()
             .addText()
                 .content("Reporte generado automáticamente.")
                 .fontSize(8)
                 .alignmentCenter()
                 .margins(40, 0, 0, 0)
                 .next()
             .build();
         System.out.println("Reporte de ventas generado correctamente.");
    }

    private static TableBuilder addDynamicSalesRows(TableBuilder tableBuilder, List<Sale> sales) {
        for (Sale sale : sales) {
            double total = sale.getQuantity() * sale.getUnitPrice();
            tableBuilder.addRow()
                .addCell()
                    .content(sale.getProductName())
                    .alignmentLeft()
                .addCell()
                    .content(String.valueOf(sale.getQuantity()))
                    .alignmentCenter()
                .addCell()
                    .content(String.format("$%.2f", total))
                    .alignmentRight()
                    .endRow();
        }
        return tableBuilder;
    }
    
    static class Sale {
        private final String productName;
        private final int quantity;
        private final double unitPrice;
        
        public Sale(String productName, int quantity, double unitPrice) {
            this.productName = productName;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
        }
        
        public String getProductName() {
            return productName;
        }
        
        public int getQuantity() {
            return quantity;
        }
        
        public double getUnitPrice() {
            return unitPrice;
        }
    }
}
```

## Referencia de la API

### DonPdf

- `builder()`: Inicia la construcción de un documento PDF
- `output(String path)`: Establece la ruta de salida del PDF
- `pageSize(PageSize pageSize)`: Establece el tamaño de página
- `backgroundColor(int r, int g, int b)`: Establece el color de fondo usando RGB
- `backgroundColor(String color)`: Establece el color de fondo usando un nombre o código hexadecimal
- `backgroundImage(String image)`: Establece una imagen de fondo
- `backgroundOpacity(float opacity)`: Establece la opacidad del fondo (0.0 - 1.0)
- `defaultFont(String fontPath)`: Establece la fuente predeterminada
- `defaultFontSize(float size)`: Establece el tamaño de fuente predeterminado
- `mergePdf(String pdfPath)`: Añade un PDF para combinar
- `build()`: Construye el documento PDF

### TextBuilder

- `content(String text)`: Establece el contenido del texto
- `font(String fontPath)`: Establece la fuente
- `fontSize(float size)`: Establece el tamaño de fuente
- `color(String color)`: Establece el color del texto usando un nombre o código hexadecimal
- `color(int r, int g, int b)`: Establece el color del texto usando RGB
- `alignment(TextAlignment alignment)`: Establece la alineación del texto
- `alignmentLeft()`, `alignmentCenter()`, `alignmentRight()`, `alignmentJustified()`, `alignmentJustifiedAll()`: Métodos de conveniencia para alineación
- `border(Border border)`: Establece el borde
- `margins(float top, float right, float bottom, float left)`: Establece los márgenes
- `next()`: Finaliza la construcción del texto y vuelve al constructor principal

### ImageBuilder

- `path(String imagePath)`: Establece la ruta de la imagen
- `size(float width, float height)`: Establece el tamaño
- `alignment(HorizontalAlignment alignment)`: Establece la alineación
- `margins(float top, float right, float bottom, float left)`: Establece los márgenes
- `next()`: Finaliza la construcción de la imagen y vuelve al constructor principal

### TableBuilder

- `width(float width)`: Establece el ancho de la tabla
- `alignment(HorizontalAlignment alignment)`: Establece la alineación
- `border(Border border)`: Establece el borde
- `margins(float top, float right, float bottom, float left)`: Establece los márgenes
- `addRow()`: Añade una nueva fila a la tabla
- `next()`: Finaliza la construcción de la tabla y vuelve al constructor principal

### RowBuilder

- `addCell()`: Añade una nueva celda a la fila
- `endRow()`: Finaliza la construcción de la fila y vuelve al constructor de la tabla

### CellBuilder

- `content(String content)`: Establece el contenido de la celda
- `font(String fontPath)`: Establece la fuente
- `fontSize(float size)`: Establece el tamaño de fuente
- `color(String color)`: Establece el color del texto usando un nombre o código hexadecimal
- `color(int r, int g, int b)`: Establece el color del texto usando RGB
- `background(String color)`: Establece el color de fondo usando un nombre o código hexadecimal
- `background(int r, int g, int b)`: Establece el color de fondo usando RGB
- `alignment(TextAlignment alignment)`: Establece la alineación del texto
- `alignmentLeft()`, `alignmentCenter()`, `alignmentRight()`, `alignmentJustified()`, `alignmentJustifiedAll()`: Métodos de conveniencia para alineación
- `border(Border border)`: Establece el borde
- `addCell()`: Finaliza la construcción de la celda actual y comienza otra
- `endRow()`: Finaliza la construcción de la celda actual y la fila, volviendo al constructor de la tabla

## Contribuir

Las contribuciones son bienvenidas. Por favor envía un Pull Request o abre un Issue.
