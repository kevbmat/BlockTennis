public class Block {
    public int centerX;
    public int centerY;
    public int height;
    public int width;
    public int topLeftX;
    public int topLeftY;

    public Block(int centerX, int centerY, int width, int height) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.width = width;
        this.height = height;
        this.topLeftX = centerX - (width / 2);
        this.topLeftY = centerY - (height / 2);
    }
}