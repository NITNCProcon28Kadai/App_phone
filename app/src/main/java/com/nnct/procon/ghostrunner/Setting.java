package com.nnct.procon.ghostrunner;

import java.io.Serializable;

/**
 * Created by kaito on 2017/09/04.
 */

public class Setting implements Serializable{
     String mode;
     String method;
     String pace;
     String courseFile;//拡張子なし
     String courseName;
     int count;
     long time;//記録時間
     double dist;//コースの距離
}
