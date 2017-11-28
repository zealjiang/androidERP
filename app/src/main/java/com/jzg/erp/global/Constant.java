package com.jzg.erp.global;

/**
 * @author: voiceofnet
 * email: pengkun@jingzhengu.com
 * phone:18101032717
 * @time: 2016/6/7 14:47
 * @desc:存放各种SharedPreference，intent跳转的key和其他常量
 */
public class Constant {
    /**
     * 确定列表标题和内容是否有重复标记
     */
    public static final String IS_TITLE = "istitle";
    //车系头部信息位置
    public static final int MODEL_HEADER = 0;
    public static final String ERROR_FORNET = "网络请求失败，请检查您的网络";

    /**
     * 拍照
     */
    public static final int REQUEST_CODE_CAMERA = 100;
    public static final int REQUEST_CODE_LOCAL = 101;
    public static final int REQUEST_CODE_GETIMAGE_BYCROP = 102;

    /**
     * 照片的临时存放地址
     */
    public static final String TEMP_PATH = "/JZGErpChe/";

    /**
     * 跳转单选页面
     */
    public static final String KEY_ARR="arr";
    public static final String KEY_TITLE="title";


    public static final String[] BIANSUXIANG={"AT","MT","AMT"};
    public static final String[] YUSUAN={"3~5万","5~6万","6~8万","8~10万","10万以上"};
    public static final String INPUT_TITLE = "input_title";
    public static final String INPUT_HINT = "input_hint";
    public static final String SHOWTEXT = "showText";
    public static final String INPUT_TYPE = "input_type";
    public static final String INPUT_LENGTH = "input_length";
    public static final String ACTIVITY_INPUT = "activity_input";
    public static final String INPUT_REQUESTCODE = "input_requestcode";
    public static final String INPUT_TIPS = "input_tips";
    public static final String OLD_DATA = "oldData";


    public static final String KEY_ACache_UserWrapper = "userwrapper";
    public static final String CARDETAIL = "车辆详情";
    public static final String MAKE_NAME = "makename";
    public static final String MODE_NAME = "modename";
    public static final String CARSOURCEID = "carsourceId ";
    public static final String CARSTATUS = "carstatus ";
    public static final String CARID = "carId";
        public static final String CARDETAILPATH = "cardetailpath";
    public static final String ISSHOWBUTTON = "isshowbutton";
    public static final String STATUSTEXTCOLOR1 = "#ffffff";
    public static final String STATUSZAISHOUCOLOR = "#4790ef";//在售背景
    public static final String STATUSYISHOUCHCOLOR = "#7a7a7a";//已售出
    public static final String STATUSYIYUDINGCOLOR = "#ff612a";//已预订
    public static final String STATUSTEXTCOLOR2 = "#5495e6";//已整备
    public static final String STATUSTEXTCOLORNO2 = "#9fa5ab";//未整备
    public static final String MONEYTEXTCOLOR = "#ff4c4d";//有价格
    public static final String NOMONEYTEXTCOLOR = "#999999";//无价格
    /**
     * 联网访问成功
     */
    public static final int SUCCESS = 1;
    public static final int FAIL = 0;

    public static final int PAGECOUNT = 20;


    //分享
    public static final String SHARE_PIC = "share_pic";
    public static final String SHARE_TEXT = "share_txt";
    public static final String SHARE_URL = "share_url";
}
