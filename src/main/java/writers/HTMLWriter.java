package writers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Map;

import restaurant.FoodItem;
import restaurant.FoodItemCategory;

import com.hp.gagawa.java.Document;
import com.hp.gagawa.java.DocumentType;
import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Br;
import com.hp.gagawa.java.elements.H1;
import com.hp.gagawa.java.elements.I;
import com.hp.gagawa.java.elements.Li;
import com.hp.gagawa.java.elements.P;
import com.hp.gagawa.java.elements.Text;
import com.hp.gagawa.java.elements.Title;
import com.hp.gagawa.java.elements.Ul;

public class HTMLWriter extends IWriter {

	@Override
	public void createDocument(Map<FoodItemCategory, FoodItem[]> items,
			String outputFilePath) throws FileNotFoundException {

		Document document = new Document(DocumentType.XHTMLTransitional);
		document.head.appendChild(new Title().appendChild(new Text("Menu")));
		Body body = document.body;

		// TODO: This should be <center> tag instead of <p>
		body.appendChild(new P().appendChild(new Text("Menu")));

		for (FoodItemCategory category : foodCategoriesPrintOrder) {
			if (items.containsKey(category)) {
				body.appendChild(new H1().appendChild(new Text(category
						.toString().toUpperCase())));
				body.appendChild(getHtmlForCategory(items.get(category)));
			}
		}

		// Save as a HTML file
		// TODO: Curate the path
		PrintWriter printer = new PrintWriter(new FileOutputStream(
				outputFilePath));
		printer.println(document.write());
		printer.close();
	}

	private Ul getHtmlForCategory(FoodItem[] breakfastItems) {
		Ul itemsList = new Ul();
		for (FoodItem item : breakfastItems) {
			Li foodDetail = new Li();
			foodDetail.appendChild(new Text(item.getName()));
			foodDetail.appendChild(new Br());
			foodDetail.appendChild(new I().appendChild(new Text(item
					.getDescription())));
			foodDetail.appendChild(new Br());
			// TODO: Add the currency code
			foodDetail.appendChild(new Text(item.getPrice()));
			itemsList.appendChild(foodDetail);
		}
		return itemsList;
	}
}
