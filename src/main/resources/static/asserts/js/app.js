let OSS = require('ali-oss');

let client = new OSS({
    region: 'oss-cn-beijing',
    accessKeyId: 'LTAIudE0rAY2SYWC',
    accessKeySecret: 'W7RDxQgpJk4gLu95heHFop4caMjo0u'
});

// putBucket 创建 Bucket
async function putBucket() {
    try {
        const result = await client.putBucket('your bucket name');
        console.log(result);
    } catch (err) {
        console.log(err);
    }
}

//查看所有 Bucket
async function listBuckets() {
    try {
        const result = await client.listBuckets();
        console.log(result);
        const result2 = await client.listBuckets({
            prefix: 'prefix',
        });
        console.log(result);
    } catch (err) {
        console.log(err);
    }
}

//删除 Bucket
async function deleteBucket() {
    try {
        const result = await client.deleteBucket('your bucket name');
        console.log(result);
    } catch (err) {
        console.log(err);
    }
}

//Bucket访问权限
async function getBucketACL() {
    try {
        const result = await client.getBucketACL('luozhang002');
        console.log(result);
    } catch (err) {
        console.log(err);
    }
}

putBucket();
listBuckets();
deleteBucket();