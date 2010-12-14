import java.io.File;

import junit.framework.TestCase;

import org.tmapi.core.TopicMap;
import org.tmapi.core.TopicMapSystemFactory;
import org.tmapix.io.CTMTopicMapReader;

/*******************************************************************************
 * Copyright 2010, Topic Map Lab ( http://www.topicmapslab.de )
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

/**
 * @author Sven Krosse
 * 
 */
public class Test extends TestCase {

	public void test() throws Exception{
		TopicMap tm = TopicMapSystemFactory.newInstance().newTopicMapSystem().createTopicMap("http://lala");
		CTMTopicMapReader reader = new CTMTopicMapReader(tm, new File("src/test/resources/ex.ctm"));
		reader.read();
	}
	
}
