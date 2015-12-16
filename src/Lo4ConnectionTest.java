
/*
 * Last changes made by $Author: andreas $, $Date: 2006-11-22 09:31:27 +0100 (Mi, 22 Nov 2006) $
 */

import com.sun.star.beans.PropertyValue;
import com.sun.star.comp.helper.Bootstrap;
import com.sun.star.document.EventObject;
import com.sun.star.document.XEventBroadcaster;
import com.sun.star.document.XEventListener;
import com.sun.star.lang.XComponent;
import com.sun.star.uno.UnoRuntime;

/**
 * @author Andreas Bröker
 * @version $Revision: 11063 $
 */
public class Lo4ConnectionTest {

	public static void main(String[] args) {
		com.sun.star.uno.XComponentContext xContext = null;

		try {
			// get the remote office component context
			xContext = Bootstrap.bootstrap();
			if (xContext != null)
				System.out.println("Connected to a running office ...");
			com.sun.star.lang.XMultiComponentFactory xMCF = xContext
					.getServiceManager();
			Object oDesktop = xMCF.createInstanceWithContext(
					"com.sun.star.frame.Desktop", xContext);
			com.sun.star.frame.XComponentLoader xCompLoader = (com.sun.star.frame.XComponentLoader) UnoRuntime
					.queryInterface(com.sun.star.frame.XComponentLoader.class,
							oDesktop);
			XComponent xComponent = xCompLoader.loadComponentFromURL(
					"private:factory/swriter", "_blank", 0,
					new PropertyValue[0]);

			XEventBroadcaster xEventBroadcaster = (XEventBroadcaster) UnoRuntime
					.queryInterface(XEventBroadcaster.class, xComponent);
			if (xEventBroadcaster != null) {
				// HERE COMES THE FIRST EXCEPTION
				xEventBroadcaster.addEventListener(new XEventListener() {

					@Override
					public void disposing(com.sun.star.lang.EventObject arg0) {
						System.out.println("disposing");
					}

					@Override
					public void notifyEvent(EventObject arg0) {
						System.out.println("notifyEvent");
					}
				});
			}

			System.exit(0);
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
	}
}
