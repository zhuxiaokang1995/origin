package com.mj.holley.ims.service.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Wanghui on 2018/3/27.
 */
public class ConstantValue {

    public static final List<String> REPAIRED_STATION_LIST = Arrays.asList("Station5","Station6");//固定的返修工位

    public static final List<String> REPEAT_STATION_LIST = Arrays.asList("Station2","Station3");//固定的重复工位如5号生产线 焊线工位

    public static final List<String> TRANSPORT_START_POINT_LIST = Arrays.asList("Point01","Point03");//物料输送线固定上料交接点
}
