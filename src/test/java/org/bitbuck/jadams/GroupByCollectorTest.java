package org.bitbuck.jadams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GroupByCollectorTest {
	
	private static class Emp{
		final private String name;
		final private String department;
		public Emp(String name, String department) {
			this.name = name;
			this.department = department;
		}
		public String getName() {
			return name;
		}
		public String getDepartment() {
			return department;
		}
		
		
	}
	
	@Test
	public void testGroupBy(){
		Emp joe=new Emp("Joe","accounting");
		Emp laura=new Emp("Laura","sales");
		Emp gus=new Emp("Gus","management");
		Emp sally=new Emp("Sally","accounting");
		Emp bill=new Emp("Bill","sales");
		Stream<Emp> stream=Stream.of(joe,sally,bill,laura,gus);
		Map<String,List<Emp>> grouped=stream.collect(MapCollector.groupBy((Emp emp) -> emp.getDepartment()));
		assertEquals(grouped.keySet().size(),3);
		assertTrue(grouped.keySet().contains("accounting"));
		assertTrue(grouped.keySet().contains("sales"));
		assertTrue(grouped.keySet().contains("management"));
		assertEquals(grouped.get("accounting").size(),2);
		assertEquals(grouped.get("sales").size(),2);
		assertEquals(grouped.get("management").size(),1);
		assertTrue(grouped.get("accounting").contains(joe));
		assertTrue(grouped.get("accounting").contains(sally));
		assertTrue(grouped.get("sales").contains(laura));
		assertTrue(grouped.get("sales").contains(bill));
		assertTrue(grouped.get("management").contains(gus));
	}

	@Test
	public void testGroupByWithNull(){
		Emp joe=new Emp("Joe","accounting");
		Emp laura=new Emp("Laura","sales");
		Emp gus=new Emp("Gus","management");
		Emp sally=new Emp("Sally","accounting");
		Emp bill=new Emp("Bill",null);
		Stream<Emp> stream=Stream.of(joe,sally,bill,laura,gus);
		Map<String,List<Emp>> grouped=stream.collect(MapCollector.groupBy((Emp emp) -> emp.getDepartment()));
		assertEquals(grouped.keySet().size(),4);
		assertTrue(grouped.keySet().contains("accounting"));
		assertTrue(grouped.keySet().contains("sales"));
		assertTrue(grouped.keySet().contains(null));
		assertEquals(grouped.get("accounting").size(),2);
		assertEquals(grouped.get("sales").size(),1);
		assertEquals(grouped.get("management").size(),1);
		assertEquals(grouped.get(null).size(),1);
		assertTrue(grouped.get("accounting").contains(joe));
		assertTrue(grouped.get("accounting").contains(sally));
		assertTrue(grouped.get("sales").contains(laura));
		assertTrue(grouped.get(null).contains(bill));
		assertTrue(grouped.get("management").contains(gus));
	}

}
