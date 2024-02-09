package com.vinicius.dscatalog.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vinicius.dscatalog.projections.IdProjection;

public class Utils {

	public static <ID> List<? extends IdProjection<ID>> replace(List<? extends IdProjection<ID>> ordered, List<? extends IdProjection<ID>> unordered) {
		Map<ID, IdProjection<ID>> map = new HashMap<>();
		unordered.forEach(x -> map.put(x.getId(), x));
		List<IdProjection<ID>> result = new ArrayList<>();
		ordered.forEach(x -> result.add(map.get(x.getId())));
		
		return result;
	}

}
