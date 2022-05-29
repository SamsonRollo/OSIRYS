package main;

import gen.ExcelLoader;

public class DummyGame extends OsirysGame{
    public DummyGame(){}

    @Override
    protected void loadGame(ExcelLoader loader) {}

    @Override
    public void revertChanges(boolean status) {}
    
}
