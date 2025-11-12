import 'package:flutter/material.dart';
import 'package:lab_5/item_models.dart';
import 'package:lab_5/details_screen.dart';
import 'package:lab_5/item_list_screen.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Lab 5 | ListView',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(
          seedColor: Colors.pinkAccent,
        ), //colour theme established with seed (makes a colour pallet using a selected colour with proximities)
      ),
      home: MyHomePage(),
      debugShowCheckedModeBanner: false,
    );
  }
}

class MyHomePage extends StatefulWidget {
  @override
  _MyHomePageState createState() => _MyHomePageState();
}


class _MyHomePageState extends State<MyHomePage> {
  var itemList = <ListViewItems>[];

  @override
  void initState() {
    super.initState();
    //populating up List w/ hardcoded items
    itemList = [
      ListViewItems('', 'test2', ''),
      ListViewItems(
        '',
        'test2',
        'List item with no title nor valid image link.',
      ),
      ListViewItems(
        'Chocolate Chip Cookie',
        'https://assets.bonappetit.com/photos/5ca534485e96521ff23b382b/1:1/w_2560%2Cc_limit/chocolate-chip-cookie.jpg',
        'A classic cookie.  Very yummy.  10/10.',
      ),
      ListViewItems('Hot Sauce', 'https://www.mariowiki.com/images/5/5f/Hot_Sauce_PMTTYDNS_icon.png?cfac0', 'This is the real spicy stuff!'),
      ListViewItems('Hot Dog', 'https://www.mariowiki.com/images/b/b9/Hot_Dog_PMTTYDNS_icon.png?36c62', 'A classic at ballparks.'),
      ListViewItems('Ripe Mango', 'https://www.mariowiki.com/images/d/dd/Keel_Mango_PMTTYDNS_icon.png?1f54b', 'From a tropical island!'),
      ListViewItems('Perfect Peach', 'https://www.mariowiki.com/images/1/11/Peachy_Peach_PMTTYDNS_icon.png?9f32f', 'They dont look this good in real life!'),
      ListViewItems('Chocolate Cake', 'https://www.mariowiki.com/images/4/41/Choco_Cake_PMTTYDNS_icon.png?698c7', 'You can never have enough chocolate...'),
      ListViewItems('Orange Juice', 'https://www.mariowiki.com/images/4/43/Fresh_Juice_PMTTYDNS_icon.png?4ba11', 'They put a slice on top so you know its orange juice.'),
      ListViewItems('Ice Cream Parfait', 'https://www.mariowiki.com/images/d/d0/Fruit_Parfait_PMTTYDNS_icon.png?ab927', 'The leading cause of brain freeze.'),
      ListViewItems('Healthy Salad', 'https://www.mariowiki.com/images/b/b5/Healthy_Salad_PMTTYDNS_icon.png?29726', 'They say the ladies love this salad.'),
      ListViewItems('Ice Pop', 'https://www.mariowiki.com/images/7/76/Icicle_Pop_PMTTYDNS_icon.png?31f60', 'A summer classic on a stick!'),
      ListViewItems('Mango Pudding', 'https://www.mariowiki.com/images/a/ae/Mango_Delight_PMTTYDNS_icon.png?3986a', 'My mango is to blow up, and then act like-'),
      ListViewItems('Omelette Lunch', 'https://www.mariowiki.com/images/7/7a/Omelette_Meal_PMTTYDNS_icon.png?22606', 'You gotta crack a few eggs to make an omelette.'),
      ListViewItems('Spaghetti with Sauce', 'https://www.mariowiki.com/images/a/a3/Spaghetti_PMTTYDNS_icon.png?627a7', 'Just like Nonnas! Well, almost.'),
	  ListViewItems('Evil Soup', 'https://www.mariowiki.com/images/2/29/Trial_Stew_PMTTYDNS_icon.png?dae27', 'probably made by a 5 year old'),
      ListViewItems('Tropical Tonic', 'https://www.mariowiki.com/File:Tasty_Tonic_PMTTYDNS_icon.png', 'How do they make it so blue?'),
      ListViewItems('Hot Tea', 'https://www.mariowiki.com/images/2/23/Koopa_Tea_PMTTYDNS_icon.png?e266c', 'Are you someone that owns too many mugs or not enough?'),
      ListViewItems('Fried Egg', 'https://www.mariowiki.com/images/9/94/Fried_Egg_PMTTYDNS_icon.png?cb69a', 'Found it on the roof of my car.'),
      ListViewItems('Halloween Candy', 'https://www.mariowiki.com/images/b/be/Honey_Candy_PMTTYDNS_icon.png?76868', 'No one will notice its gone!'),
    ];

    //Checcking if title text is empty
    for (var items in itemList) {
      items.checkValidTitle();
    }
  }

  ///Loads main screen with list of items
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("Lab 5 | ListView")),
      body: ListView.builder(
        itemCount: itemList.length,
        itemBuilder: (context, position) {
          ///we refactored our list node, so it generates by passing the list items
          return ItemCard(itemList: itemList, position: position);
        },
      ), // use the below ListView codes here to gain a better understanding of ListView.
    );
  }
}


