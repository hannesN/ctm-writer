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
package de.topicmapslab.ctm.writer.test.construct;

import java.io.ByteArrayOutputStream;

import org.tmapi.core.Association;
import org.tmapi.core.Locator;
import org.tmapi.core.Topic;

import de.topicmapslab.ctm.writer.core.CTMTopicMapWriter;
import de.topicmapslab.ctm.writer.test.BaseTestCase;
import de.topicmapslab.identifier.TmdmSubjectIdentifier;

/**
 * @author Sven Krosse
 * 
 */
public class TestTopicExport extends BaseTestCase {

	public void testAko() throws Exception {
		Topic type = createTopicBySI("type");
		Topic assoType = topicMap.createTopicBySubjectIdentifier(topicMap
				.createLocator(TmdmSubjectIdentifier.TMDM_SUPERTYPE_SUBTYPE_ASSOCIATION));
		Topic[] topics = new Topic[100];
		for (int i = 0; i < topics.length; i++) {
			Topic supertype = createTopicBySI("supertype-" + i);
			Association a = topicMap.createAssociation(assoType);
			a.createRole(topicMap.createTopicBySubjectIdentifier(topicMap
					.createLocator(TmdmSubjectIdentifier.TMDM_SUBTYPE_ROLE_TYPE)), type);
			a.createRole(topicMap.createTopicBySubjectIdentifier(topicMap
					.createLocator(TmdmSubjectIdentifier.TMDM_SUPERTYPE_ROLE_TYPE)), supertype);
			topics[i] = supertype;
		}

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		CTMTopicMapWriter writer = new CTMTopicMapWriter(stream, base);
		writer.write(topicMap);
		String content = stream.toString();
		System.out.println(content);
		for (Topic st : topics) {
			for (Locator si : st.getSubjectIdentifiers()) {
				String value = si.toExternalForm();			
				assertTrue("Writer export the supertype " + value + " as literal ako <" + value + ">;",
						content.contains("ako <" + value + ">"));
			}
		}
	}

}
