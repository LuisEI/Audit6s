package utilidades;

public class Utilidades {

    //Constantes de la tabla Division
    public static final String TABLA_DIVISION = "t_division";
    public static final String CAMPO_ID_DIVISION = "id_division";
    public static final String CAMPO_DIVISION = "division";

    public static final String CREAR_TABLA_DIVISION = "CREATE TABLE "+ TABLA_DIVISION +" ("+
            CAMPO_ID_DIVISION +" INTEGER, "+ CAMPO_DIVISION +" TEXT)";

    //Constantes de la tabla Area
    public static final String TABLA_AREA = "t_area";
    public static final String CAMPO_ID_AREA = "id_area";
    public static final String CAMPO_AREA = "area";
    public static final String CAMPO_LIDER = "lider";
    public static final String CAMPO_ID_PLANTA = "id_planta";

    public static final String CREAR_TABLA_AREA = "CREATE TABLE " + TABLA_AREA + " (" +
            CAMPO_ID_AREA + " INTEGER, " + CAMPO_AREA + " TEXT, " + CAMPO_LIDER + " TEXT, " + CAMPO_ID_PLANTA + " INTEGER)";

    //Constantes de la tabla Gerentes
    public static final String TABLA_GERENTE = "t_gerente";
    public static final String CAMPO_ID_GERENTE = "id_gerente";
    public static final String CAMPO_GERENTE = "gerente";

    public static final String CREAR_TABLA_GERENTE = "CREATE TABLE " + TABLA_GERENTE + " (" + CAMPO_ID_GERENTE + " INTEGER, " +
            CAMPO_GERENTE + " TEXT, " + CAMPO_ID_PLANTA + " INTEGER)";

    //Constantes de la tabla Auditor
    public static final String TABLA_AUDITOR = "t_auditor";
    public static final String CAMPO_ID_AUDITOR = "id_auditor";
    public static final String CAMPO_AUDITOR = "auditor";

    public static final String CREAR_TABLA_AUDITOR = "CREATE TABLE " + TABLA_AUDITOR + " (" + CAMPO_ID_AUDITOR + " INTEGER, "+
            CAMPO_AUDITOR + " TEXTO)";

    //Constantes de la tabla Planta
    public static final String TABLA_PLANTA = "t_planta";
    public static final String CAMPO_PLANTA = "planta";

    public static final String CREAR_TABLA_PLANTA = "CREATE TABLE " + TABLA_PLANTA + " (" + CAMPO_ID_PLANTA + " INTEGER, " +
            CAMPO_PLANTA + " TEXT, " + CAMPO_ID_DIVISION + " INTEGER)";

    //Constantes de la tabla Hallazgos
    public static final String TABLA_HALLAZGOS = "t_hallazgos";
    public static final String CAMPO_ID_HALLAZGO = "id_hallazgo";
    public static final String CAMPO_HALLAZGO = "hallazgo";

    public static final String CREAR_TABLA_HALLAZGOS = "CREATE TABLE " + TABLA_HALLAZGOS + " (" + CAMPO_ID_HALLAZGO + " INTEGER, " +
            CAMPO_HALLAZGO + " TEXT)";

    //Constantes de la tabla Detalle
    public static final String TABLA_DETALLE = "t_detalle";
    public static final String CAMPO_ID_DETALLE = "id_detalle";
    public static final String CAMPO_DESCRIPCION = "descripcion";

    public static final String CREAR_TABLA_DETALLE = "CREATE TABLE " + TABLA_DETALLE + " (" +
            CAMPO_ID_HALLAZGO + " INTEGER, "+
            CAMPO_ID_DETALLE + " INTEGER, "+
            CAMPO_DESCRIPCION + " TEXT)";

    //Constantes de la tabla Auditoria
    public static final String TABLA_AUDITORIA = "t_auditoria";
    public static final String CAMPO_ID_AUDITORIA = "id_auditoria";
    public static final String CAMPO_TURNO = "turno";
    public static final String CAMPO_FECHA = "fecha";
    public static final String CAMPO_S1_OBS_1 = "s1_obs_1";
    public static final String CAMPO_S1_OBS_2 = "s1_obs_2";
    public static final String CAMPO_S1_OBS_3 = "s1_obs_3";
    public static final String CAMPO_S2_OBS_1 = "s2_obs_1";
    public static final String CAMPO_S2_OBS_2 = "s2_obs_2";
    public static final String CAMPO_S2_OBS_3 = "s2_obs_3";
    public static final String CAMPO_S2_OBS_4 = "s2_obs_4";
    public static final String CAMPO_S3_OBS_1 = "s3_obs_1";
    public static final String CAMPO_S3_OBS_2 = "s3_obs_2";
    public static final String CAMPO_S3_OBS_3 = "s3_obs_3";
    public static final String CAMPO_S3_OBS_4 = "s3_obs_4";
    public static final String CAMPO_S4_OBS_1 = "s4_obs_1";
    public static final String CAMPO_S4_OBS_2 = "s4_obs_2";
    public static final String CAMPO_S4_OBS_3 = "s4_obs_3";
    public static final String CAMPO_S4_OBS_4 = "s4_obs_4";
    public static final String CAMPO_S5_OBS_1 = "s5_obs_1";
    public static final String CAMPO_S5_OBS_2 = "s5_obs_2";
    public static final String CAMPO_S5_OBS_3 = "s5_obs_3";
    public static final String CAMPO_S5_OBS_4 = "s5_obs_4";
    public static final String CAMPO_RES_S1 = "res_s1";
    public static final String CAMPO_RES_S2 = "res_s2";
    public static final String CAMPO_RES_S3 = "res_s3";
    public static final String CAMPO_RES_S4 = "res_s4";
    public static final String CAMPO_RES_S5 = "res_s5";
    public static final String CAMPO_RES_TOTAL = "res_total";

    public static final String CREAR_TABLA_AUDITORIA = "CREATE TABLE " + TABLA_AUDITORIA + " (" +
            CAMPO_ID_AUDITORIA + " INTEGER, " +
            CAMPO_AREA + " INTEGER, " +
            CAMPO_GERENTE + " INTEGER, " +
            CAMPO_AUDITOR + " INTEGER, " +
            CAMPO_TURNO + " INTEGER, " +
            CAMPO_FECHA + " TEXT, " +
            CAMPO_S1_OBS_1 + " INTEGER, " +
            CAMPO_S1_OBS_2 + " INTEGER, " +
            CAMPO_S1_OBS_3 + " INTEGER, " +
            CAMPO_S2_OBS_1 + " INTEGER, " +
            CAMPO_S2_OBS_2 + " INTEGER, " +
            CAMPO_S2_OBS_3 + " INTEGER, " +
            CAMPO_S2_OBS_4 + " INTEGER, " +
            CAMPO_S3_OBS_1 + " INTEGER, " +
            CAMPO_S3_OBS_2 + " INTEGER, " +
            CAMPO_S3_OBS_3 + " INTEGER, " +
            CAMPO_S3_OBS_4 + " INTEGER, " +
            CAMPO_S4_OBS_1 + " INTEGER, " +
            CAMPO_S4_OBS_2 + " INTEGER, " +
            CAMPO_S4_OBS_3 + " INTEGER, " +
            CAMPO_S4_OBS_4 + " INTEGER, " +
            CAMPO_S5_OBS_1 + " INTEGER, " +
            CAMPO_S5_OBS_2 + " INTEGER, " +
            CAMPO_S5_OBS_3 + " INTEGER, " +
            CAMPO_S5_OBS_4 + " INTEGER, " +
            CAMPO_RES_S1 + " REAL, " +
            CAMPO_RES_S2 + " REAL, " +
            CAMPO_RES_S3 + " REAL, " +
            CAMPO_RES_S4 + " REAL, " +
            CAMPO_RES_S5 + " REAL, " +
            CAMPO_RES_TOTAL + " REAL)";

}
