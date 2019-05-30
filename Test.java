

package org.apache.lucene.index;

import java.io.IOException;


public class OneMergeHelper {
    private OneMergeHelper() {}
    public static String getSegmentName(MergePolicy.OneMerge merge) {
        return merge.info != null ? merge.info.info.name : "_na_";
    }


    public static long getTotalBytesWritten(Thread thread,
                                            MergePolicy.OneMerge merge) throws IOException {
   
        if (thread instanceof ConcurrentMergeScheduler.MergeThread) {
            return ((ConcurrentMergeScheduler.MergeThread) thread).rateLimiter
                .getTotalBytesWritten();
        }
        assert false: "this is not merge thread";
        return merge.totalBytesSize();
    }


}
