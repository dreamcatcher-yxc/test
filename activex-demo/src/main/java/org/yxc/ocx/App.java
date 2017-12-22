package org.yxc.ocx;

import com.jacob.com.ComThread;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
//        String dllPath = "D:\\devInstall\\maven\\repo\\net\\sf\\jacob-project\\jacob\\1.14.3\\jacob-1.14.3-x64.dll";
//        System.setProperty(LibraryLoader.JACOB_DLL_PATH, dllPath);
//        LibraryLoader.loadJacobLibrary();
//        String[] propArr = System.getProperty("java.library.path").split(";");
//        for(String prop : propArr) {
//            System.out.println(prop);
//        }
//        ComThread.InitSTA();// 初始化COM线程
        // 注册表中取得注册MyOcx.dll的ProgId，或clsid。
        // BC044A60-23CC-4F5C-8C16-476648435AC1
        // 2434023D-2325-4BBB-840E-86124EA4B27F
            String clsId1 = "clsid:BC044A60-23CC-4F5C-8C16-476648435AC1";
        String clsId2 = "clsid:2434023D-2325-4BBB-840E-86124EA4B27F";
        String clsId3 = "clsid:4364f170-81a9-11ce-9c32-00aa0051e517";
        String programId = "CID7000ID.CID7000idCtrl.1";
//        ActiveXComponent com = ActiveXComponent.createNewInstance(clsId1);
        ActiveXComponent com = new ActiveXComponent(programId);//在MyOcx中搜索ProgID = s 'MyOcx.MyDialog.1'
        // Dispatch对象看成是对Activex控件的一个操作
        Dispatch disp = (Dispatch) com.getObject();
        // 假设MsgBox是MyOcx.dll中的一个方法
//        Dispatch.call(disp, "CID_IDCARD_OpenDevice", null);
        ComThread.Release();// 结束进程
        ComThread.Release();// 操作完成后，释放COM线程
    }
}
