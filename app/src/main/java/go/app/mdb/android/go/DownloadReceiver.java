package go.app.mdb.android.go;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import java.io.File;

public class DownloadReceiver extends BroadcastReceiver {
	
	private static String LOG_TAG = DownloadReceiver.class.getSimpleName();

	@SuppressLint("NewApi")
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
			
			//Toast.makeText(context, "Download finished", Toast.LENGTH_LONG).show();
//			DownloadManager manager = new DownloadManager(context.getContentResolver(), 
//													context.getPackageName());
//			Intent myIntent = new Intent(context, DownloadService.class);
//			context.startService(myIntent);
			
			DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
			
			//install the apk	
			Bundle bundle = intent.getExtras();
			long id = bundle.getLong(DownloadManager.EXTRA_DOWNLOAD_ID, 0);

			Uri filePath = manager.getUriForDownloadedFile(id);
			
			if (filePath != null) {
				Log.i(LOG_TAG, filePath.getPath());
//				if(Build.VERSION.SDK_INT < 23){
//					Intent installIntent = new Intent(Intent.ACTION_VIEW);
//					installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//					installIntent.setDataAndType(filePath,
//							"application/vnd.android.package-archive");
//					
//					context.startActivity(installIntent);
//				}else{
////					GlobalData.getRootPath()
					DownloadManager.Query query = new DownloadManager.Query();
					 query.setFilterById(id);
			            Cursor c = manager.query(query);
			            String filename=null;
			            if(c.moveToFirst()){
			            	//��ȡ�ļ�����·��
			                filename = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
			                
			            }
			            Toast.makeText(context, ""+filename, Toast.LENGTH_SHORT).show();
//					File apkFile = new File(Environment.getExternalStorageDirectory() + "/downloads/"+"" + "iPK.apk");
					File apkFile = new File(filename);
					Intent var2 = new Intent();
			        var2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			        var2.setAction("android.intent.action.VIEW");
			        String var3 = getMIMEType(apkFile);
			        var2.setDataAndType(Uri.fromFile(apkFile), var3);
			        try {
			        	context.startActivity(var2);
			        } catch (Exception var5) {
			            var5.printStackTrace();
			            Toast.makeText(context, "û���ҵ��򿪴����ļ��ĳ���", Toast.LENGTH_SHORT).show();
			        }
//				}
					
				
				
				
				
				
//				GlobalData.setDownloadFinished(id);
				
				
				
				
				
				

			}
			
		}
	}
	public String getMIMEType(File var0) {
        String var1 = "";
        String var2 = var0.getName();
        String var3 = var2.substring(var2.lastIndexOf(".") + 1, var2.length()).toLowerCase();
        var1 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(var3);
        return var1;
    }
}
