package com.pz.rock.core.protocol.parser;

import java.io.File;
import java.util.List;

import com.pz.rock.core.protocol.structure.Model;

public interface ProtocolParserI {
	
	public List<Model>  parse(File file );

}
