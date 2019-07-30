package base;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;

import com.google.common.collect.Lists;

public class TestInitListArrayList {
	@Test
	public void test() {
		List<Long> cates = Lists.newArrayList();
		if (cates==null) {
			System.out.println("cates==null  true");
		}
		if (CollectionUtils.isEmpty(cates)) {
			System.out.println("CollectionUtils.isEmpty(cates)   true");
		}
	}
}
