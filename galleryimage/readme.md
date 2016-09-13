###鸿洋大神的相册效果
两种实现方式，HorizontalScrollView 和 RecycleView（推荐）

ScrollView 是滚动距离>图片宽度 然后set大图图片，并且移除前一张 加载后一张。（相反类似）
RecycleView是不停的拿到第一张图片，图片只要改变，就set给大图。更流畅简单。