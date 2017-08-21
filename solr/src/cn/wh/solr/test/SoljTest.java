package cn.wh.solr.test;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.Test;

public class SoljTest {

	private HttpSolrServer httpSolrServer;

	@Before
	public void init() {

		// 创建HttpSolrServer连接对象
		String baseURL = "http://localhost:8100/solr/";
		this.httpSolrServer = new HttpSolrServer(baseURL);

	}

	/**
	 * 简单查询
	 * 
	 * @throws Exception
	 */
	@Test
	public void query() throws Exception {
		// 创建查询对象
		SolrQuery query = new SolrQuery();
		
		// 设置查询条件
		query.setQuery("title:solr");
		
		// 发起搜索请求
		QueryResponse response = this.httpSolrServer.query(query);
		
		// 处理搜索结果
		SolrDocumentList results = response.getResults();
		System.out.println("搜索到的结果总数为："+results.getNumFound());
		
		// 遍历搜索结果
		for (SolrDocument solrDocument : results) {
			System.out.println("id:"+solrDocument.get("id").toString());
			System.out.println("title:"+solrDocument.get("title").toString());
		}

	}

	/**
	 * 删除
	 * 
	 * @throws Exception
	 */
	@Test
	public void delete() throws Exception {
		// 根据HttpSolrServer唯一域删除数据
		// this.httpSolrServer.deleteById("c200");

		// 根据条件删除所有
		this.httpSolrServer.deleteByQuery("*:*");

		// 提交
		httpSolrServer.commit();
	}

	/**
	 * 新增和修改
	 * 
	 * @throws Exception
	 */
	@Test
	public void saveAndUpdate() throws Exception {

		// 创建SolrInputDocument对象，封装要新增和要修改的数据
		SolrInputDocument document = new SolrInputDocument();

		// 放入数据
		document.addField("id", "c400");
		document.addField("title", "lucene&solr");
		document.addField("weight", 55);

		// 使用HttpSolrServer连接对象执行新增和修改
		this.httpSolrServer.add(document);

		// 提交
		this.httpSolrServer.commit();
	}
}
