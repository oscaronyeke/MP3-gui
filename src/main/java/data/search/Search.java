package data.search;

import data.api.call.APICall;
import data.search.result.Results;

/**
 * File: Search.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public interface Search {

    Results getResults(APICall call);

}
