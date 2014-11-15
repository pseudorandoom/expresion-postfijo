package com.ciencias;

import com.ciencias.core.Parser;
import com.ciencias.view.JView;

public class App
{
    public static void main( String[] args )
    {
        new JView();
        //System.out.println( new Parser("1+2*3+5").parse().prefix() );
    }
}
