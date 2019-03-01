package go.app.mdb.android.go;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.net.Uri;

public class FileDownloader {

	private Activity _context;
	private DownloadManager _manager;
//	private String language;
	private String LOG_TAG = FileDownloader.class.getSimpleName();
	
	public interface OnFinishHandler {
		public void onFinish(boolean bSuccess);
	}
	
	private OnFinishHandler _handler;
	
	public void setOnFinishHandler(OnFinishHandler handler) {
		_handler = handler;
	}
	
	public FileDownloader(Activity context) {
		// TODO Auto-generated constructor stub
		_context = context;
//		_manager = new DownloadManager(_context.getContentResolver(), 
//										_context.getPackageName());
//		
//		Intent intent = new Intent(_context, DownloadService.class);
//		_context.startService(intent);
		
		_manager = (DownloadManager) _context.getSystemService(Context.DOWNLOAD_SERVICE);
	}
	
//	protected long downloadHelper(game game){
//		 
//		String url = game.getDownloadurl();
//		if (url.isEmpty()) {
//			
//			if("zh".equals(HelperUtils.getLanguage(_context)))
//			{
//				Toast.makeText(_context, "���ص�ַΪ��", Toast.LENGTH_LONG).show();
//			}else if("en".equals(HelperUtils.getLanguage(_context)))
//			{
//				Toast.makeText(_context, "Download address is empty!", Toast.LENGTH_LONG).show();
//			}
////			else if("id".equals(HelperUtils.getLanguage(activity)))
//			else
//			{
//				Toast.makeText(_context, "Alamat Unduhan Kosong!", Toast.LENGTH_LONG).show();
//			}
//			return -1;
//		}
//		if (!url.startsWith("http://") && !url.startsWith("https://")) {
//			if("zh".equals(HelperUtils.getLanguage(_context)))
//			{
//				Toast.makeText(_context, "�Ƿ������ص�ַ", Toast.LENGTH_LONG).show();
//			}else if("en".equals(HelperUtils.getLanguage(_context)))
//			{
//				Toast.makeText(_context, "Illegal download address!", Toast.LENGTH_LONG).show();
//			}
//			else
////			else if("id".equals(HelperUtils.getLanguage(_context)))
//			{
//				Toast.makeText(_context, "Alamat Unduhan Illegal!", Toast.LENGTH_LONG).show();
//			}
//			return -1;
//		}
//		
//		Uri uri = Uri.parse(game.getDownloadurl());
//		Request request = new Request(uri);
//		
//		//ֻ����wifi����������
//		request.setAllowedNetworkTypes(Request.NETWORK_WIFI | Request.NETWORK_MOBILE);
//		//��ֹ����״̬������
//		request.setAllowedOverRoaming(true);  
//		//request.setDestinationUri(Uri.fromFile(apkFile));
//		
//		request.setDestinationInExternalFilesDir(_context, "downloads", game.getGameName());
//		
//		request.setTitle(game.getGameName());
//		request.setDescription(game.getGameIntro());
//		request.setVisibleInDownloadsUi(true);
//		
//		//request.setShowRunningNotification(true);		
//		request.setNotificationVisibility(Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);	
//		
//		long id = _manager.enqueue(request);	
//		GlobalData.addDownload(id, game);
//		
//		Log.i(LOG_TAG, "id: " + id);
//		
//		return id;		
//	}
	
	
//	public long downloadGame(final game gameInfo) {
//		
//		
//		
//		return downloadHelper(gameInfo);
//		
//		
//	}
	
	public void downloadFile(final String urlPath, final String fileName) {
//		String rootPath = GlobalData.getRootPath();
//		if (rootPath.isEmpty()) {
//				Toast.makeText(_context, "�޷�����SD��", Toast.LENGTH_LONG).show();
//			return;
//		}
		
		
//		final File apkFile = new File(Environment.getExternalStorageDirectory() +"/" + fileName);
//		if (apkFile.exists()) {
//			
//			//��Ϸ�Ѿ�����  �Ƿ��������
//			
//		} else {
			downloadHelper(urlPath, fileName);
//		}	
		
	}
	
	
public void downloadHelper(String urlPath, String fileName){
		Uri uri = Uri.parse(urlPath);
		Request request = new Request(uri);
		//ֻ����wifi����������
		request.setAllowedNetworkTypes(Request.NETWORK_WIFI | Request.NETWORK_MOBILE);
		//��ֹ����״̬������
		request.setAllowedOverRoaming(true);  
		//request.setDestinationUri(Uri.fromFile(apkFile));
//		request.setDestinationInExternalFilesDir(_context, "downloads", fileName);
		request.setDestinationInExternalPublicDir("downloads", fileName);
		request.setTitle(fileName);
		request.setDescription(fileName);
		request.setVisibleInDownloadsUi(true);
		//request.setShowRunningNotification(true);		
		request.setNotificationVisibility(Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);	
		_manager.enqueue(request);
	}

}
