package sujin.realtimetrip.config.s3;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey; // AWS 액세스 키

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey; // AWS 비밀 키

    @Value("${cloud.aws.region.static}")
    private String region; // AWS S3 서비스 리전

    /**
     * Amazon S3 클라이언트를 생성하고 초기화하는 빈을 제공합니다.
     * @return 초기화된 Amazon S3 클라이언트 객체
     */
    @Bean
    public AmazonS3Client amazonS3Client() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }
}