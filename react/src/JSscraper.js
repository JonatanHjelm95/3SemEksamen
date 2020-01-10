const request = require('request');
const cheerio = require('cheerio');
const url = "https://www.dba.dk/samlekort-magic-the-gathering/id-1061412244/";


request(url,{mode: 'no-cors' },(error,response,html)=>{
    if(!error&&response.statusCode==200){
        const $ = cheerio.load(html);
        const thing = $('.vip-additional-text');
        console.log(thing.text());        
    }
});

