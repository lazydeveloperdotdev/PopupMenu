# PopupMenu
Customizable android popup menu with icon support
<h1>Usage</h1>

```
val menu = PopupMenu(<context>)
            val option1 = PopupMenu.Option(R.drawable.ic_favorite_yes, "Option 1")
            option1.setSelectListener(object : PopupMenu.Option.SelectListener {
                override fun onSelect() {
                  //do something
                }
            })

            val option2 = PopupMenu.Option(R.drawable.ic_favorite_yes, "Option 2")
            option2.setSelectListener(object : PopupMenu.Option.SelectListener {
                override fun onSelect() {
                  //do something
                }
            })

            menu.addMenu(option1)
            menu.addMenu(option2)
            menu.showAt(<anchorView>)
```
