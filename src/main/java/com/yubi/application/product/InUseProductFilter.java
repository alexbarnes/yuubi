package com.yubi.application.product;

import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermDocs;
import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.Filter;
import org.apache.lucene.util.OpenBitSet;

public class InUseProductFilter extends Filter {
	private static final long serialVersionUID = -2262704382542619377L;

	@Override
	public DocIdSet getDocIdSet(IndexReader reader) throws IOException {
		OpenBitSet bitSet = new OpenBitSet( reader.maxDoc() );
        TermDocs termDocs = reader.termDocs( new Term( "onDisplay", "true" ) );
        while ( termDocs.next() ) {
            bitSet.set( termDocs.doc() );
        }
        return bitSet;
	}

}
