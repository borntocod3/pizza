//
//  QRReaderViewController.m
//  pizza_sample
//
//  Created by YokoyamaDaisuke on 2015/10/21.
//  Copyright © 2015年 YokoyamaDaisuke. All rights reserved.
//

#import "QRReaderViewController.h"
#import <AVFoundation/AVFoundation.h>
#import "HoloViewController.h"
#import "AlertView.h"

@interface QRReaderViewController () <AVCaptureMetadataOutputObjectsDelegate>

@property (nonatomic, strong) AVCaptureSession *session;

@end

@implementation QRReaderViewController

- (void)viewDidLoad
{
    [super viewDidLoad];

    _session = [[AVCaptureSession alloc] init];
    
    NSArray *devices = [AVCaptureDevice devicesWithMediaType:AVMediaTypeVideo];
    AVCaptureDevice *device = nil;
    AVCaptureDevicePosition camera = AVCaptureDevicePositionBack; // Back or Front
    for (AVCaptureDevice *d in devices) {
        device = d;
        if (d.position == camera) {
            break;
        }
    }
    
    NSError *error = nil;
    AVCaptureDeviceInput *input = [AVCaptureDeviceInput deviceInputWithDevice:device
                                                                        error:&error];
    [_session addInput:input];
    
    AVCaptureMetadataOutput *output = [AVCaptureMetadataOutput new];
    [output setMetadataObjectsDelegate:self queue:dispatch_queue_create("myQueue.metadata", DISPATCH_QUEUE_SERIAL)];
    [_session addOutput:output];
    
    // QR コードのみ
    output.metadataObjectTypes = @[AVMetadataObjectTypeQRCode];

    [_session startRunning];
    
    AVCaptureVideoPreviewLayer *preview = [AVCaptureVideoPreviewLayer layerWithSession:self.session];
    preview.frame = self.view.bounds;
    preview.videoGravity = AVLayerVideoGravityResizeAspectFill;
    [self.view.layer insertSublayer:preview atIndex:0];
    //[self.view.layer addSublayer:preview];
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    [self startScanning];
}

- (void)viewWillDisappear:(BOOL)animated
{
    [self stopScanning];
    
    [super viewWillDisappear:animated];
}

- (IBAction)close:(id)sender
{
    [self dismissViewControllerAnimated:YES completion:nil];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


#pragma mark - Controlling Reader

- (void)startScanning;
{
    if (![_session isRunning]) {
        [_session startRunning];
    }
}

- (void)stopScanning;
{
    if ([_session isRunning]) {
        [_session stopRunning];
    }
}

#pragma mark - AVCaptureMetadataOutputObjects Delegate Methods

- (void)captureOutput:(AVCaptureOutput *)captureOutput didOutputMetadataObjects:(NSArray *)metadataObjects fromConnection:(AVCaptureConnection *)connection
{
    for (AVMetadataObject *current in metadataObjects) {
        
        if ([current isKindOfClass:[AVMetadataMachineReadableCodeObject class]]
            && [current.type isEqualToString:AVMetadataObjectTypeQRCode]) {
            
            NSString *scannedResult = [(AVMetadataMachineReadableCodeObject *) current stringValue];
            
            [self stopScanning];
            
            NSLog(@"%@", scannedResult);
            
            if (_delegate && [_delegate respondsToSelector:@selector(reader:didScanResult:)]) {
                [_delegate reader:self didScanResult:scannedResult];
            }

            break;
        }
    }
}

@end
