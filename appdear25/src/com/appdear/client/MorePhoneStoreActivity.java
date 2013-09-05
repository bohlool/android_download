package com.appdear.client;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.LocalActivityManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.LinearLayout.LayoutParams;

import com.appdear.bdmap.Location;
import com.appdear.client.commctrls.BaseActivity;
import com.appdear.client.commctrls.BaseGroupActivity;
import com.appdear.client.exception.ApiException;
import com.appdear.client.exception.ServerException;
import com.appdear.client.service.AppContext;
import com.appdear.client.service.Constants;
import com.appdear.client.service.SoftFormTags;
import com.appdear.client.service.api.ApiCityRequest;
import com.appdear.client.service.api.ApiCitylistRequest;
import com.appdear.client.service.api.ApiUserResult;

public class MorePhoneStoreActivity extends BaseGroupActivity{
	/**
	 * ��������б���
	 */
	ApiCitylistRequest cityResult;
	private LocalActivityManager manager;
//	private String[] channels = {"htc","lenovo","huawei","samsung"};
	private String[] channels = {"HTC","����","����","��Ϊ"};
	private static HashMap<String,String> map=new HashMap();
	private static HashMap<String,String> productmap=new HashMap();
	String array [] = {"����","����","�½�","�㶫","�㽭","���","����","���ɹ�","����","����","����","����","����","����","ɽ��","�ຣ","�Ĵ�","����","�ӱ�","����","����","����","����","����","����","�Ϻ�","����","����","ɽ��","����","������"};
	String array1[][] = {{"����"},
			{"����"},
			{"������","����","����","����","��ʲ","��������","�����","����","ʯ����","����","��³ľ��","����","����","����̩","��³��"},
			{"����","��ɽ","����","��ݸ","��ͷ","��ɽ","����","����","տ��","÷��","����","����","����","����","�麣","ï��","��Զ","��Դ","��β","�ع�","�Ƹ�","̨ɽ","�⴨"},
			{"����","����","̨��","����","��","����","����","��ɽ","��ˮ","����","����"},
			{"���"},
			{"����","���","����","�ӳ�","����","����","����","��ɫ","����","����","���Ǹ�","����","����","����","��ɫ"},
			{"����������","�����׶�","��ͷ","���","������˹","���ͺ���","���ױ���","ͨ��","�ں�","�����첼","���ֺ���","�˰���","���ֹ�����"},
			{"����","��ԭ","����"},
			{"�ϲ�","����","Ƽ��","����","�˴�","����","����","������","�Ž�","����","ӥ̶"},
			{"����","ͭ��","����","����","����ˮ","�Ͻ�","����","��˳","����","ǭ��"},
			{"����","����","�Ϸ�","����","ͭ��","����","����","����","����","����","����","��ɽ","����","����","��ɽ","����","�ߺ�"},
			{"����","����","����","����","ͭ��","μ��","����","����","����","�Ӱ�"},
			{"��ɽ","��Ϫ","����","����","����","����ɽ","��˳","����","��«��","����","�̽�","����","����","Ӫ��","����"},
			{"����","��ͬ","����","����","�ٷ�","����","˷��","̫ԭ","����","��Ȫ","�˳�"},
			{"����","���ľ"},
			{"�ɶ�","��ɽ","�Թ�","�˱�","����","����","����","��ɽ","��֦��","�ϳ�","��Ԫ","üɽ","�ڽ�","�Ű�","����","����","����","�㰲","����","����"},
			{"�Ͼ�","��","�γ�","̩��","��ͨ","����","���Ƹ�","��Ǩ","����","����","����","����","����"},
			{"����","����","�е�","����","��ˮ","�ȷ�","�ػʵ�","ʯ��ׯ","��ɽ","��̨","�żҿ�"},
			{"����"},
			{"����","����","��ƽ","Ȫ��","����","����","����","����","����"},
			{"�׳�","��ɽ","����","����","��Դ","��ƽ","��ԭ","ͨ��","�ӱ�","����","÷�ӿ�","�Ӽ�"},
			{"����","��","����","�Ĳ�","����","����"},
			{"�人","��ʩ","�˲�","����","����","ʮ��","����","�差","����","��ʯ","Т��","����","�Ƹ�","Ǳ��","����"},
			{"����","��ɽ","�����","��Ϫ","����","��ɽ","�ն�","����","�ٲ�","��ͨ","����","�º�","����","������","��˫����"},
			{"�Ϻ�"},
			{"����","����","���","��Ȫ","����","����","ƽ��","��ˮ","¤��","����","��Ҵ","����"},
			{"��ɳ","����","����","����","����","��̶","����","����","¦��","����","����","����","�żҽ�","����"},
			{"����","�ൺ","��ׯ","Ϋ��","̩��","����","����","�Ͳ�","��Ӫ","����","�ĳ�","����","��̨","����","����","����","����"},
			{"����","���","����","���","ƽ��ɽ","����Ͽ","�ܿ�","����","����","���","פ���","����","�ױ�","��Դ","����","����","����","֣��"},
			{"����","������","�׸�","�ں�","����","��ľ˹","ĵ����","�������","�绯","˫Ѽɽ","��̨��","����"}};
	static{
		/*map.put("����","CIT000000000004");map.put("����","CIT000000000032");map.put("����","CIT000000000249");
		map.put("����","CIT000000000162");map.put("��ƽ","CIT000000000163");map.put("�żҽ�","CIT000000000185");
		map.put("����","CIT000000000187");map.put("��̶","CIT000000000189");
		map.put("����","CIT000000000191");map.put("����","CIT000000000192");
		map.put("����","CIT000000000194");map.put("��ɳ","CIT000000000195");map.put("ʮ��","CIT000000000108");
		map.put("����","CIT000000000109");map.put("�人","CIT000000000113");map.put("�差","CIT000000000116");
		map.put("̨��","CIT000000000152");map.put("����","CIT000000000153");map.put("����","CIT000000000154");
		map.put("����","CIT000000000155");map.put("����","CIT000000000158");map.put("����","CIT000000000092");
		map.put("����","CIT000000000095");map.put("����","CIT000000000096");map.put("����","CIT000000000098");
		map.put("����","CIT000000000099");map.put("���","CIT000000000104");map.put("֣��","CIT000000000105");map.put("פ���","CIT000000000106");
		map.put("����","CIT000000000041");map.put("��ɽ","CIT000000000042");
		map.put("�żҿ�","CIT000000000044");map.put("����","CIT000000000046");map.put("ʯ��ׯ","CIT000000000047");
		map.put("��̨","CIT000000000050");
		map.put("����","CIT000000000051");
		map.put("�Ž�","CIT000000000172");
		map.put("�ϲ�","CIT000000000173");
		map.put("����","CIT000000000177");
		map.put("������","CIT000000000178");
		map.put("����","CIT000000000180");
		map.put("��ͨ","CIT000000000139");
		map.put("��Ǩ","CIT000000000140");
		map.put("����","CIT000000000141");
		map.put("����","CIT000000000142");
		map.put("����","CIT000000000143");
		map.put("����","CIT000000000144");
		map.put("̩��","CIT000000000145");
		map.put("�γ�","CIT000000000147");
		map.put("����","CIT000000000148");
		map.put("��ɽ","CIT000000000197");
		map.put("����","CIT000000000198");
		map.put("��Դ","CIT000000000205");
		map.put("����","CIT000000000206");
		map.put("��Զ","CIT000000000207");
		map.put("տ��","CIT000000000208");
		map.put("����","CIT000000000211");
		map.put("��ɽ","CIT000000000334");
		map.put("��ͬ","CIT000000000054");
		map.put("̫ԭ","CIT000000000055");
		map.put("��Ȫ","CIT000000000062");
		map.put("��Ӫ","CIT000000000063");
		map.put("����","CIT000000000064");
		map.put("����","CIT000000000065");
		map.put("����","CIT000000000066");
		map.put("����","CIT000000000067");
		map.put("��ׯ","CIT000000000068");
		map.put("̩��","CIT000000000069");
		map.put("����","CIT000000000070");
		map.put("�Ͳ�","CIT000000000071");
		map.put("����","CIT000000000072");
		map.put("Ϋ��","CIT000000000073");
		map.put("��̨","CIT000000000074");
		map.put("�ĳ�","CIT000000000075");
		map.put("����","CIT000000000076");
		map.put("����","CIT000000000077");
		map.put("�ൺ","CIT000000000078");
		map.put("����","CIT000000000335");
		map.put("����","CIT000000000121");
		map.put("�Ϸ�","CIT000000000123");
		map.put("����","CIT000000000125");
		map.put("����","CIT000000000126");
		map.put("����","CIT000000000127");
		map.put("����","CIT000000000128");
		map.put("����","CIT000000000130");
		map.put("����","CIT000000000131");
		map.put("�ߺ�","CIT000000000132");
		map.put("����","CIT000000000133");
		map.put("ͭ��","CIT000000000134");
		map.put("��ɽ","CIT000000000136");
		map.put("����","CIT000000000001");
		map.put("�����첼","CIT000000000079");
		map.put("��ͷ","CIT000000000082");
		map.put("���ױ���","CIT000000000083");
		map.put("���ͺ���","CIT000000000084");
		map.put("����","CIT000000000238");
		map.put("����","CIT000000000240");
		map.put("����","CIT000000000241");
		map.put("���","CIT000000000243");
		map.put("�Ϻ�","CIT000000000002");*/
		productmap.put("����","samsung");
		productmap.put("����","samsung");
		productmap.put("��Ϊ","huawei");
		productmap.put("HTC","htc");
		map.put("�Ϻ�","CIT100053");
		map.put("����","CIT100040");
		map.put("��ɽ","CIT100041");
		map.put("�����","CIT100042");
		map.put("��Ϫ","CIT100043");
		map.put("����","CIT100044");
		map.put("��ɽ","CIT100045");
		map.put("�ն�","CIT100046");
		map.put("����","CIT100047");
		map.put("�ٲ�","CIT100048");
		map.put("��ͨ","CIT100049");
		map.put("����","CIT100050");
		map.put("�º�","CIT100051");
		map.put("����","CIT100052");
		map.put("������","CIT100307");
		map.put("��˫����","CIT100308");
		map.put("����������","CIT100255");
		map.put("�����׶�","CIT100256");
		map.put("��ͷ","CIT100257");
		map.put("���","CIT100258");
		map.put("������˹","CIT100259");
		map.put("���ͺ���","CIT100260");
		map.put("���ױ���","CIT100261");
		map.put("ͨ��","CIT100262");
		map.put("�ں�","CIT100263");
		map.put("�����첼","CIT100264");
		map.put("���ֺ���","CIT100265");
		map.put("�˰���","CIT100317");
		map.put("���ֹ�����","CIT100329");
		map.put("����","CIT100163");
		map.put("�׳�","CIT100226");
		map.put("��ɽ","CIT100227");
		map.put("����","CIT100228");
		map.put("����","CIT100229");
		map.put("��Դ","CIT100230");
		map.put("��ƽ","CIT100231");
		map.put("��ԭ","CIT100232");
		map.put("ͨ��","CIT100233");
		map.put("�ӱ�","CIT100234");
		map.put("����","CIT100327");
		map.put("÷�ӿ�","CIT100328");
		map.put("�Ӽ�","CIT100334");
		map.put("�ɶ�","CIT100015");
		map.put("��ɽ","CIT100016");
		map.put("�Թ�","CIT100017");
		map.put("�˱�","CIT100018");
		map.put("����","CIT100019");
		map.put("����","CIT100020");
		map.put("����","CIT100021");
		map.put("��ɽ","CIT100022");
		map.put("��֦��","CIT100023");
		map.put("�ϳ�","CIT100024");
		map.put("��Ԫ","CIT100025");
		map.put("üɽ","CIT100026");
		map.put("�ڽ�","CIT100027");
		map.put("�Ű�","CIT100028");
		map.put("����","CIT100029");
		map.put("����","CIT100030");
		map.put("����","CIT100290");
		map.put("�㰲","CIT100291");
		map.put("����","CIT100292");
		map.put("����","CIT100293");
		map.put("���","CIT100294");
		map.put("����","CIT100266");
		map.put("��ԭ","CIT100330");
		map.put("����","CIT100331");
		map.put("����","CIT100000");
		map.put("����","CIT100001");
		map.put("�Ϸ�","CIT100080");
		map.put("����","CIT100081");
		map.put("ͭ��","CIT100082");
		map.put("����","CIT100083");
		map.put("����","CIT100084");
		map.put("����","CIT100085");
		map.put("����","CIT100086");
		map.put("����","CIT100087");
		map.put("����","CIT100088");
		map.put("��ɽ","CIT100089");
		map.put("����","CIT100158");
		map.put("����","CIT100159");
		map.put("��ɽ","CIT100160");
		map.put("����","CIT100161");
		map.put("�ߺ�","CIT100162");
		map.put("����","CIT100066");
		map.put("�ൺ","CIT100067");
		map.put("��ׯ","CIT100068");
		map.put("Ϋ��","CIT100069");
		map.put("̩��","CIT100070");
		map.put("����","CIT100071");
		map.put("����","CIT100072");
		map.put("�Ͳ�","CIT100073");
		map.put("��Ӫ","CIT100074");
		map.put("����","CIT100075");
		map.put("�ĳ�","CIT100076");
		map.put("����","CIT100077");
		map.put("��̨","CIT100078");
		map.put("����","CIT100079");
		map.put("����","CIT100268");
		map.put("����","CIT100269");
		map.put("����","CIT100319");
		map.put("����","CIT100270");
		map.put("��ͬ","CIT100271");
		map.put("����","CIT100272");
		map.put("����","CIT100273");
		map.put("�ٷ�","CIT100274");
		map.put("����","CIT100275");
		map.put("˷��","CIT100276");
		map.put("̫ԭ","CIT100277");
		map.put("����","CIT100278");
		map.put("��Ȫ","CIT100279");
		map.put("�˳�","CIT100280");
		map.put("����","CIT100135");
		map.put("��ɽ","CIT100136");
		map.put("����","CIT100137");
		map.put("��ݸ","CIT100138");
		map.put("��ͷ","CIT100139");
		map.put("��ɽ","CIT100140");
		map.put("����","CIT100141");
		map.put("����","CIT100142");
		map.put("տ��","CIT100143");
		map.put("÷��","CIT100144");
		map.put("����","CIT100145");
		map.put("����","CIT100146");
		map.put("����","CIT100147");
		map.put("����","CIT100148");
		map.put("�麣","CIT100149");
		map.put("ï��","CIT100175");
		map.put("��Զ","CIT100176");
		map.put("��Դ","CIT100177");
		map.put("��β","CIT100178");
		map.put("�ع�","CIT100179");
		map.put("�Ƹ�","CIT100180");
		map.put("̨ɽ","CIT100312");
		map.put("�⴨","CIT100313");
		map.put("����","CIT100150");
		map.put("���","CIT100151");
		map.put("����","CIT100152");
		map.put("�ӳ�","CIT100153");
		map.put("����","CIT100154");
		map.put("����","CIT100155");
		map.put("����","CIT100156");
		map.put("��ɫ","CIT100181");
		map.put("����","CIT100182");
		map.put("����","CIT100183");
		map.put("���Ǹ�","CIT100184");
		map.put("����","CIT100185");
		map.put("����","CIT100186");
		map.put("����","CIT100187");
		map.put("��ɫ","CIT100322");
		map.put("������","CIT100295");
		map.put("����","CIT100296");
		map.put("����","CIT100297");
		map.put("����","CIT100298");
		map.put("��ʲ","CIT100299");
		map.put("��������","CIT100300");
		map.put("�����","CIT100301");
		map.put("����","CIT100302");
		map.put("ʯ����","CIT100303");
		map.put("����","CIT100304");
		map.put("��³ľ��","CIT100305");
		map.put("����","CIT100306");
		map.put("����","CIT100321");
		map.put("����̩","CIT100332");
		map.put("��³��","CIT100333");
		map.put("�Ͼ�","CIT100090");
		map.put("��","CIT100091");
		map.put("�γ�","CIT100092");
		map.put("̩��","CIT100093");
		map.put("��ͨ","CIT100094");
		map.put("����","CIT100095");
		map.put("���Ƹ�","CIT100096");
		map.put("��Ǩ","CIT100097");
		map.put("����","CIT100098");
		map.put("����","CIT100100");
		map.put("����","CIT100101");
		map.put("����","CIT100102");
		map.put("����","CIT100103");
		map.put("�ϲ�","CIT100126");
		map.put("����","CIT100127");
		map.put("Ƽ��","CIT100128");
		map.put("����","CIT100129");
		map.put("�˴�","CIT100130");
		map.put("����","CIT100235");
		map.put("����","CIT100236");
		map.put("������","CIT100237");
		map.put("�Ž�","CIT100238");
		map.put("����","CIT100239");
		map.put("ӥ̶","CIT100240");
		map.put("����","CIT100193");
		map.put("����","CIT100194");
		map.put("�е�","CIT100195");
		map.put("����","CIT100196");
		map.put("��ˮ","CIT100197");
		map.put("�ȷ�","CIT100198");
		map.put("�ػʵ�","CIT100199");
		map.put("ʯ��ׯ","CIT100200");
		map.put("��ɽ","CIT100201");
		map.put("��̨","CIT100202");
		map.put("�żҿ�","CIT100203");
		map.put("����","CIT100003");
		map.put("���","CIT100004");
		map.put("����","CIT100005");
		map.put("���","CIT100006");
		map.put("ƽ��ɽ","CIT100007");
		map.put("����Ͽ","CIT100008");
		map.put("�ܿ�","CIT100009");
		map.put("����","CIT100010");
		map.put("����","CIT100011");
		map.put("���","CIT100012");
		map.put("פ���","CIT100013");
		map.put("����","CIT100014");
		map.put("�ױ�","CIT100204");
		map.put("��Դ","CIT100205");
		map.put("����","CIT100206");
		map.put("����","CIT100207");
		map.put("����","CIT100208");
		map.put("֣��","CIT100209");
		map.put("����","CIT100054");
		map.put("����","CIT100055");
		map.put("̨��","CIT100057");
		map.put("����","CIT100058");
		map.put("��","CIT100059");
		map.put("����","CIT100060");
		map.put("����","CIT100061");
		map.put("��ɽ","CIT100062");
		map.put("��ˮ","CIT100063");
		map.put("����","CIT100064");
		map.put("����","CIT100065");
		map.put("����","CIT100157");
		map.put("��","CIT100190");
		map.put("����","CIT100191");
		map.put("�Ĳ�","CIT100192");
		map.put("����","CIT100311");
		map.put("����","CIT100324");
		map.put("�人","CIT100104");
		map.put("��ʩ","CIT100105");
		map.put("�˲�","CIT100106");
		map.put("����","CIT100107");
		map.put("����","CIT100108");
		map.put("ʮ��","CIT100109");
		map.put("����","CIT100110");
		map.put("�差","CIT100111");
		map.put("����","CIT100112");
		map.put("��ʯ","CIT100113");
		map.put("Т��","CIT100114");
		map.put("����","CIT100220");
		map.put("�Ƹ�","CIT100221");
		map.put("Ǳ��","CIT100222");
		map.put("����","CIT100223");
		map.put("��ɳ","CIT100115");
		map.put("����","CIT100116");
		map.put("����","CIT100117");
		map.put("����","CIT100118");
		map.put("����","CIT100119");
		map.put("��̶","CIT100120");
		map.put("����","CIT100121");
		map.put("����","CIT100122");
		map.put("¦��","CIT100123");
		map.put("����","CIT100124");
		map.put("����","CIT100125");
		map.put("����","CIT100224");
		map.put("�żҽ�","CIT100225");
		map.put("����","CIT100326");
		map.put("����","CIT100166");
		map.put("����","CIT100167");
		map.put("���","CIT100168");
		map.put("��Ȫ","CIT100169");
		map.put("����","CIT100170");
		map.put("����","CIT100171");
		map.put("ƽ��","CIT100172");
		map.put("��ˮ","CIT100173");
		map.put("¤��","CIT100174");
		map.put("����","CIT100210");
		map.put("��Ҵ","CIT100309");
		map.put("����","CIT100310");
		map.put("����","CIT100002");
		map.put("����","CIT100056");
		map.put("��ƽ","CIT100099");
		map.put("Ȫ��","CIT100131");
		map.put("����","CIT100132");
		map.put("����","CIT100133");
		map.put("����","CIT100134");
		map.put("����","CIT100164");
		map.put("����","CIT100165");
		map.put("����","CIT100031");
		map.put("����","CIT100033");
		map.put("ͭ��","CIT100034");
		map.put("����","CIT100035");
		map.put("����","CIT100036");
		map.put("����ˮ","CIT100037");
		map.put("�Ͻ�","CIT100038");
		map.put("����","CIT100039");
		map.put("��˳","CIT100188");
		map.put("����","CIT100189");
		map.put("ǭ��","CIT100323");
		map.put("��ɽ","CIT100241");
		map.put("��Ϫ","CIT100242");
		map.put("����","CIT100243");
		map.put("����","CIT100244");
		map.put("����","CIT100245");
		map.put("����ɽ","CIT100246");
		map.put("��˳","CIT100247");
		map.put("����","CIT100248");
		map.put("��«��","CIT100249");
		map.put("����","CIT100250");
		map.put("�̽�","CIT100251");
		map.put("����","CIT100252");
		map.put("����","CIT100253");
		map.put("Ӫ��","CIT100254");
		map.put("����","CIT100316");
		map.put("����","CIT100032");
		map.put("����","CIT100281");
		map.put("����","CIT100282");
		map.put("����","CIT100283");
		map.put("����","CIT100284");
		map.put("ͭ��","CIT100285");
		map.put("μ��","CIT100286");
		map.put("����","CIT100287");
		map.put("����","CIT100288");
		map.put("����","CIT100289");
		map.put("�Ӱ�","CIT100320");
		map.put("����","CIT100267");
		map.put("���ľ","CIT100318");
		map.put("����","CIT100211");
		map.put("������","CIT100212");
		map.put("�׸�","CIT100213");
		map.put("�ں�","CIT100214");
		map.put("����","CIT100215");
		map.put("��ľ˹","CIT100216");
		map.put("ĵ����","CIT100217");
		map.put("�������","CIT100218");
		map.put("�绯","CIT100219");
		map.put("˫Ѽɽ","CIT100314");
		map.put("��̨��","CIT100315");
		map.put("����","CIT100325");
	}
	
	private LinearLayout bestListLayout; 
	private final static int POINTLISTEND=2;
	private final static int USEREND=1;
	private final static int STROECHANGE=3;
	private final static int INITCITY=4;
	private final static int DEFAULTCITY=5;
	private ApiUserResult result;
	private AutoCompleteTextView s_area,s_area1,s_channel;
	ProgressDialog progress;
	private String text;
	private ImageView button,button1,button2;
	private String s_areaid;
	private  ArrayAdapter adapter,adapter1 ;
	private View pointlistview=null;
	ImageButton btn_return;
	private LinearLayout tab_ll_linear;
	boolean backbutton_is_show;
	private int currentid=0;
	private String currentPro="",currentCity="";
	
	public void onCreate(Bundle b){
		super.onCreate(b); 
		Intent intent =getIntent();
		if(intent!=null){
			String from=intent.getStringExtra("from");
			if(from!=null&&from.equals("moreView")){
				backbutton_is_show=true;
			}else{
				backbutton_is_show=false;
			}
		}
		
		setContentView(R.layout.user_phone_store);
		isShowAlert = false;
      }
	
	@Override
	public void init() {
		
		bestListLayout= (LinearLayout) this.findViewById(
				R.id.common_list_layout);
		manager=getLocalActivityManager();
		 s_area=(AutoCompleteTextView) this.findViewById(R.id.s_area);
		 adapter = new ArrayAdapter(this,R.layout.store, array);
		 
//		 currentPro=array[0];
		 s_area.setAdapter(adapter);
		 s_area.setOnItemClickListener(autoListItemClickListener);
		 
		 s_area1=(AutoCompleteTextView) this.findViewById(R.id.s_area1);
//		 adapter1 = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line, array1[0]);
//		 s_area1.setAdapter(adapter1);
		 s_area1.setOnItemClickListener(autoListItemClickListener);
//		 currentCity=array1[0][0];
		 s_channel=(AutoCompleteTextView) this.findViewById(R.id.s_channel);
		 String tempChannel=getChannel();
		 if(tempChannel.equals("htc")){
			 s_channel.setText(channels[0]);
		 }else if(tempChannel.equals("huawei")){
			 s_channel.setText(channels[3]);
		 }else if(tempChannel.equals("lenovo")){
			 s_channel.setText(channels[2]);
		 }else{
			 s_channel.setText(channels[1]);
		 }
		 
		 s_channel.setAdapter(new ArrayAdapter<String>(MorePhoneStoreActivity.this, R.layout.store, channels));
		 s_channel.setOnItemClickListener(autoListItemClickListener);
		 //adapter = new ArrayAdapter(this,R.layout.store, array);
		 button=(ImageView)this.findViewById(R.id.button);
		 button1=(ImageView)this.findViewById(R.id.button1);
		 button2=(ImageView)this.findViewById(R.id.button2);
		 s_area1.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					currentid=R.id.s_area1;
					s_area1.showDropDown();
				}    	
	        });
		 s_area.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				currentid=R.id.s_area;
				s_area.showDropDown();
			}    	
        });
		 s_channel.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					currentid=R.id.s_channel;
					s_channel.showDropDown();
				}    	
	        });
		 tab_ll_linear = (LinearLayout) findViewById(R.id.ll_navigation);
			tab_ll_linear.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});
		 button.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					currentid=R.id.s_area;
					s_area.showDropDown();
				}    	
	        });
		 button1.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					currentid=R.id.s_area1;
					s_area1.showDropDown();
				}    	
	        });
		 button2.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					currentid=R.id.s_channel;
					s_channel.showDropDown();
				}    	
	        });
		 btn_return = (ImageButton) findViewById(R.id.btn_return);
			btn_return.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					finish();
				}
			});
	}
	
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
 
			switch(msg.what){
			case 0:
					progress= ProgressDialog.show(MorePhoneStoreActivity.this, "���ݼ�����..", "��ȴ�", true, false);
					break;
			case USEREND:
				result=(ApiUserResult)msg.obj;
				break;
			case POINTLISTEND:
				View view=(View)msg.obj;
				DisplayMetrics metrics=com.appdear.client.utility.ServiceUtils.getMetrics(MorePhoneStoreActivity.this.getWindowManager());
				LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT);
				view.setLayoutParams(params);
				bestListLayout.removeAllViews();
				bestListLayout.addView((View)msg.obj);
				if(progress!=null)progress.dismiss();
				break;
			case STROECHANGE:
				updateUI();
				break;
			case INITCITY:
				if(adapter!=null){
					s_area.setAdapter(adapter);
		    		adapter.notifyDataSetChanged();
				}
				break;
			case DEFAULTCITY:
				handlerStore(msg.obj.toString());
				break;
			default:return;
			}
		}
	};
    @SuppressWarnings("unchecked")
	@Override
	public void initData() {
//    	try {
//    		/**
//    		 * ����Ϊ�գ����� �������еĳ���
//    		 */
//    		 cityResult = ApiCityRequest.citylistRequest("");
//    		 array = cityResult.citys.split(",");
//    		 if(Constants.DEBUG)Log.i("responseStr", "responseStr.citys,"+cityResult.citys);
//    		 if (array.length > 0) {
//	    		 if (array.length == 2) {
//	    			 adapter = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line, new String [] {array[0]});
//	    		 } else {
//	    			 adapter = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line, array);
//	    		 }
//    		 } else {
//    			 adapter = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line, new String []{});
//    			 adapter.notifyDataSetChanged();
//    		 }
//		} catch (ServerException e) {
//			showException(e);
//			e.printStackTrace();
//		} catch (ApiException e) {
//			showException(e);
//			e.printStackTrace();
//		}catch(Exception e){
//			
//		}
//		if(Location.currentCity!=null){
//			String city=Location.currentCity;
//			if(city.endsWith("��")){
//				city=city.substring(0,city.lastIndexOf("��"));
//			}
//			if(map.containsKey(city)){
//				Message msg=handler.obtainMessage();
//				msg.what=DEFAULTCITY;
//				msg.obj=city;
//				handler.sendMessage(msg);
//			}
//		}
    	super.initData();
	}
 
	@Override
	public void updateUI() {
		handler.sendEmptyMessage(INITCITY);
		String city=Location.currentCity;
		if(city!=null&&!city.equals("")){
			city=city.endsWith("��")?city.substring(0,city.lastIndexOf("��")):city;
			if(map.containsKey(city)){
				s_area1.setText(city);
				int opt=handlerCity(city);
				s_area.setText(array[opt]);
				adapter = new ArrayAdapter(this,R.layout.store, array);
				s_area.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				adapter1 = new ArrayAdapter(MorePhoneStoreActivity.this,R.layout.store, array1[opt]);
				s_area1.setAdapter(adapter1);
				handlerStore(city);
			}else{
				defaultUi();
			}
		}else{
			defaultUi();
		}
		super.updateUI();
	}
	public void defaultUi(){
		System.out.println(s_channel.getText().toString()+">>>>>>>>>>>>");
		pointlistview = manager.startActivity(
		           s_area.getText().toString(),
		           new Intent(MorePhoneStoreActivity.this, StoreListActivity.class).
		           putExtra("first",true).putExtra("area", map.get(currentCity.trim())).putExtra("androidchchode", "".equals(s_channel.getText().toString().trim())?getChannel():productmap.get(s_channel.getText().toString()))).getDecorView();//
		Message message=Message.obtain();
		message.what=POINTLISTEND;
		message.obj=pointlistview;	
		handler.sendMessage(message);
	}
	OnItemClickListener autoListItemClickListener =  new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
		  if(currentid==R.id.s_area){
			  
			  if(currentPro!=null&&!currentPro.equals("")&&currentPro.equals(s_area.getText().toString())){
				  return;
			  }
			  currentPro=array[arg2];
			  currentCity=array1[arg2][0];
			  adapter1 = new ArrayAdapter(MorePhoneStoreActivity.this,R.layout.store, array1[arg2]);
			  s_area1.setText(array1[arg2][0]);
			  s_area1.setAdapter(adapter1);
			  handlerStore(null);
		  }else if(currentid==R.id.s_area1){
			  if(currentCity!=null&&!currentCity.equals("")&&currentCity.equals(s_area1.getText().toString())){
				  return;
			  }
			  handlerStore(null);
		 }else if(currentid==R.id.s_channel){
			 System.out.println(s_channel.getId()+"----------"+s_channel.getText().toString());
			  if(currentCity!=null&&!currentCity.equals("")&&currentCity.equals(s_channel.getText().toString())){
				  return;
			  }
			  handlerStore(null);//TODO
		 }
		}
	};
	private void handlerStore(String city){
		 if(city==null){
		  currentCity=s_area1.getText().toString();
		 }else{
			 currentCity=city;
		 }
		  if(Constants.DEBUG)Log.i("MorePhoneStoreActivity", "MorePhoneStoreActivity   updateUI ");
		   handler.sendEmptyMessage(0);//s_channel.getId()==View.NO_ID?"samsung":channels[s_channel.getId()-1]
		   System.out.println(s_channel.getText().toString()+">>>>>>>>>>>>");
		   pointlistview=manager.startActivity( 
             s_area1.getText().toString(),
              new Intent(MorePhoneStoreActivity.this, StoreListActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).
              putExtra("area",map.get(currentCity.trim())).putExtra("androidchchode", "".equals(s_channel.getText().toString().trim())?getChannel():productmap.get(s_channel.getText().toString()))
            ).getDecorView();
			Message message=Message.obtain();
			message.what=POINTLISTEND;
			message.obj=pointlistview;	
			handler.sendMessage(message);
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0&&AppContext.userreg==true) {
			AppContext.userreg=false;
			Intent userIntent = new Intent();
			//��ת��MainActivity �����Ǹ�ҳ�����Flag������Ҫ����ͼ
			userIntent.setClass(this, MainActivity.class);
			userIntent.putExtra(SoftFormTags.ACTIVITY_SWITCH_FLAG, SoftFormTags.USER_LIST_CENTER);
			startActivity(userIntent);
            return true;
        }
      

		return super.onKeyDown(keyCode, event);
	}
	
	public int handlerCity(String city){
		for(int i=0;i<array1.length;i++){
			for(int j=0;j<array1[i].length;j++){
				if(array1[i][j].equals(city)){
					return i;
				}
			}
		}
		return -1;
	}
	
	private String getChannel(){
		String result ="";
		String company = android.os.Build.BRAND.toLowerCase();
		if(company.indexOf("htc")!=-1){
			result = "htc";
    	}else if(company.indexOf("huawei")!=-1){
    		result = "huawei";
    	}else if(company.indexOf("lenovo")!=-1){
    		result = "lenovo";
    	}else{
    		result = "samsung";
    	}
		return result;
	}
	
}
