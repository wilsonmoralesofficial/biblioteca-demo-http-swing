package segurosgyt.wsoc;

public class ViewConstants {
    //Campos de elementos por defecto
    public static final String[] fieldsFormCopyBook = {"ISBN", "Estado Fisico"};
    public static final String[] columnsCopyBook = {"ISBN", "Estado Fisico", "Fecha de Ingreso"};
    public static final String[] fieldsFormBook = {"Título", "Autor","Año Publicación"};
    public static final String[] columnsBook = {"Titulo","Autor","Año de Publicación"};

    // Listado de Libros
    public static final String textButtonCreateBook = "Registrar Libro";
    public static final String textButtonEditBook = "Editar Libro Selecionado";
    public static final String textButtonDeleteBook = "Eliminar Libro Selecionado";

    //Texto de botones de formulario de creación/edición de libros y listado de ejemplares
    public static final String textButtonBack = "Volver";
    public static final String textButtonSaveData = "Guardar Datos";
    public static final String textButtonCreateCopyBook = "Agregar Ejemplar";

    //Mensajes de los dialogos que se muestran a l usuario por cada acción
    public static final String textSaveSuccessDataMessageDialog = "Datos guardados con exito";
    public static final String textErrorToSaveDataMessageDialog =  "Ocurrio un error al realizar la consulta de datos, consulte a IT";
    public static final String textSelectBookToEditMessageDialog = "Seleccione un libro para poder editarlo";
    public static final String textDeleteBookSuccessMessageDialog = "Se eliminó el libro con exito";
    public static final String textDeleteBookErrorMessageDialog = "Fallo en la eliminación del libro, consulte con IT";
    public static final String textSelectABookToDeleteMessageDialog = "Seleccione un libro para poder eliminarlo.";
    public static final String textErrorGetBooksMessageDialog= "Ocurrio un error al realizar la consulta de libros, consulte a IT";
    public static final String textErrorToGetCopyBooksMessageDialog= "Ocurrio un error al realizar la consulta de ejemplares, consulte a IT";
    public static final String textErrorToSaveCopyBooksMessageDialog = "Ocurrio un error al almacenar los datos verifique que el numero de inventaria no sea uno que ya ingresó antes o contacte a soporte";
    public static final String tittleInitialFrame = "Bibloteca de Colaboradores Seguros G&T";


    //Texto en Blanco de tamaño 0 para usos generales(Ejemplo : Inicializar campos de formulario).
    public static final String blankText = "";

}
